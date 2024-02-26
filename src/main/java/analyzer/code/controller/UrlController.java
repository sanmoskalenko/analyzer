package analyzer.code.controller;

import analyzer.code.dto.Alert;
import analyzer.code.dto.UrlPage;
import analyzer.code.repository.UrlRepository;
import analyzer.code.service.UrlService;
import analyzer.code.utils.JtePagePath;
import analyzer.code.utils.JteTemplateEngine;
import analyzer.code.utils.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.ValidationException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

public class UrlController {

    private static final String INVALID_URL = "Invalid URL";
    private static final String URL_ALREADY_EXIST = "Page already exists";
    private static final String URL_ADDED_SUCCESS = "Page successfully added";

    private static boolean isUrl(String value) {
        try {
            new URL(value);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public static void index(Context ctx) {
        var page = UrlService.getUrls();
        var alertMessage = ctx.consumeSessionAttribute(JteTemplateEngine.FLASH_MESSAGE_KEY);
        var alertType = ctx.consumeSessionAttribute(JteTemplateEngine.FLASH_TYPE_KEY);
        var alert = new Alert(String.valueOf(alertMessage), (Alert.TYPE) alertType);
        page.setAlert(alert);
        ctx.render(JtePagePath.URLS, Collections.singletonMap("page", page));
    }

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var urlPage = UrlService.getUrlById(id);
        ctx.render(JtePagePath.SHOW, Collections.singletonMap("page", urlPage));
    }

    public static void create(Context ctx) {
        try {
            var name = ctx.formParamAsClass("url", String.class)
                    .check(UrlController::isUrl, INVALID_URL)
                    .getOrThrow(ValidationException::new);

            var urlName = UrlService.normalizeUrl(name);
            if (UrlRepository.findByName(urlName).isPresent()) {
                ctx.sessionAttribute(JteTemplateEngine.FLASH_MESSAGE_KEY, URL_ALREADY_EXIST);
                ctx.sessionAttribute(JteTemplateEngine.FLASH_TYPE_KEY, Alert.TYPE.INFO);
            } else {
                UrlService.create(urlName);
                ctx.sessionAttribute(JteTemplateEngine.FLASH_MESSAGE_KEY, URL_ADDED_SUCCESS);
                ctx.sessionAttribute(JteTemplateEngine.FLASH_TYPE_KEY, Alert.TYPE.SUCCESS);
            }
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (ValidationException e) {
            ctx.status(HttpStatus.UNPROCESSABLE_CONTENT.getCode());
            var invalidPage = new UrlPage();
            invalidPage.setValidationErrors(e.getErrors());
            ctx.render(JtePagePath.INDEX, Collections.singletonMap("page", invalidPage));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR.getCode());
            ctx.render(JtePagePath.ERROR_500);
        }
    }
}

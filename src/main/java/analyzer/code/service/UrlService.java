package analyzer.code.service;

import analyzer.code.dto.UrlPage;
import analyzer.code.dto.UrlsPage;
import analyzer.code.model.Url;
import analyzer.code.repository.UrlRepository;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlService {

    public static String normalizeUrl(String value) {
        try {
            var url = new URL(value);
            var host = url.getHost();
            var port = url.getPort();
            var protocol = url.getProtocol();

            if (port == -1) {
                return protocol + "://" + host;
            }

            return protocol + "://" + host + ":" + port;

        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static UrlsPage getUrls() {
        var urls = UrlRepository.getEntities();
        var urlsPage = new UrlsPage();
        urlsPage.setUrls(urls);

        for (var url : urlsPage.getUrls()) {
            var checks = UrlCheckService.getChecksByUrlId(url.getId());
            url.setChecks(checks);
        }

        return urlsPage;
    }

    public static UrlPage getUrlById(Long id) {
        var url = UrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var urlPage = new UrlPage(url);
        var checks = UrlCheckService.getChecksByUrlId(url.getId());
        urlPage.setChecks(checks);

        return urlPage;
    }

    public static void create(String name) {
        var url = new Url();
        url.setName(name);
        UrlRepository.save(url);
    }
}

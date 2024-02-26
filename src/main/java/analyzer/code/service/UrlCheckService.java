package analyzer.code.service;

import analyzer.code.dto.Alert;
import analyzer.code.dto.UrlCheckComponent;
import analyzer.code.dto.UrlPage;
import analyzer.code.model.Url;
import analyzer.code.repository.UrlCheckRepository;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;

import java.util.List;

public class UrlCheckService {

    private static final String CHECKED_SUCCESS = "Page successfully verified";
    private static final String INCORRECT_ADDRESS = "Invalid address";

    private static analyzer.code.model.UrlCheck xmlToUrlCheck(String html, Long urlId, int statusCode) {
        var doc = Jsoup.parse(html);
        String h1 = "";
        var h1El = doc.body().getElementsByTag("h1").first();
        if (h1El != null) {
            h1 = h1El.text();
        }
        var description = doc.head()
                .children()
                .select("meta[name=description]")
                .eachAttr("content")
                .stream()
                .findFirst()
                .orElse("");

        return analyzer.code.model.UrlCheck.builder()
                .withUrlId(urlId)
                .withH1(h1)
                .withTitle(doc.title())
                .withDescription(description)
                .withStatusCode(statusCode)
                .build();
    }

    protected static List<UrlCheckComponent> getChecksByUrlId(Long urlId) {
        var checks = UrlCheckRepository.findById(urlId);
        return checks.stream()
                .map(UrlCheckComponent::new)
                .toList();
    }

    public static UrlPage create(Url url) {
        var urlId = url.getId();
        var urlPage = new UrlPage(url);

        try {
            var response = Unirest.get(url.getName()).asString();
            Unirest.shutDown();
            var check = xmlToUrlCheck(response.getBody(), urlId, response.getStatus());
            UrlCheckRepository.save(check);
            var successAlert = new Alert(CHECKED_SUCCESS, Alert.TYPE.SUCCESS);
            var checks = UrlCheckRepository.findById(urlId).stream()
                    .map(UrlCheckComponent::new)
                    .toList();
            urlPage.setChecks(checks);
            urlPage.setAlert(successAlert);
        } catch (Exception e) {
            var checks = UrlCheckRepository.findById(urlId).stream()
                    .map(UrlCheckComponent::new)
                    .toList();
            urlPage.setChecks(checks);
            var alert = new Alert(INCORRECT_ADDRESS, Alert.TYPE.ERROR);
            urlPage.setAlert(alert);
        }

        return urlPage;
    }

}

package analyzer.code.dto;

import analyzer.code.model.Url;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UrlsPage extends BasePage {

    private List<UrlPage> urls;

    public final void setUrls(List<Url> urls) {
        this.urls = urls.stream()
                .map(UrlPage::new)
                .toList();
    }

}

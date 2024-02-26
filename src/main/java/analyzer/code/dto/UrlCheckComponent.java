package analyzer.code.dto;

import analyzer.code.model.UrlCheck;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class UrlCheckComponent {

    private Long id;
    private int statusCode;
    private String title;
    private String h1;
    private String description;
    private String createdAt;

    public UrlCheckComponent(UrlCheck check) {
        this.id = check.getId();
        this.statusCode = check.getStatusCode();
        this.title = check.getTitle();
        this.h1 = check.getH1();
        this.description = check.getDescription();
        this.createdAt = check.getCreatedAt().toLocalDateTime().format(BasePage.formatter);
    }
}

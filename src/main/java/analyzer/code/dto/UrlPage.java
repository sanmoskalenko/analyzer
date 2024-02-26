package analyzer.code.dto;

import analyzer.code.model.Url;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UrlPage extends BasePage {

    private Long id;
    private String name;
    private String createdAt;
    private List<UrlCheckComponent> checks;

    public UrlPage(Url url) {
        this.id = url.getId();
        this.name = url.getName();
        this.createdAt = url.getCreatedAt().toLocalDateTime().format(formatter);
        this.checks = new ArrayList<>();
    }

    public final UrlCheckComponent getLastCheck() {
        return checks.stream()
                .findFirst()
                .orElse(null);
    }
}

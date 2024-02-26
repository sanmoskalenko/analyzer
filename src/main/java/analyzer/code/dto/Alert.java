package analyzer.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Alert {

    public enum TYPE {
        INFO, SUCCESS, ERROR
    }

    private String message;
    private TYPE type;

}

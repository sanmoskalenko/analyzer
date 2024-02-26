package analyzer.code.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class Url {

    private Long id;
    private String name;
    private Timestamp createdAt;

}

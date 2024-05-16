package mat.model;

import lombok.Data;

@Data
public class Person {
    private Integer id;
    private String firstName;
    private String lastName;
    private Position position;
    private Organization office;
}

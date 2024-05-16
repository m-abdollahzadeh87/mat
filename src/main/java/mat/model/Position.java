package mat.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Position {
    private Integer id;
    private String name;
    private String description;

    @Override
    public String toString() {
        //toString for specifying null Name
        return Objects.toString(name, "no name");
    }
}

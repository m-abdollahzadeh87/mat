package mat.model;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class Organization {
    private Integer id;
    private String name;
    private List<Position> chart;

    @Override
    public String toString() {
        return Objects.toString(name, "no name");
    }

}

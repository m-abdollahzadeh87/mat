package mat.model;

import lombok.Data;

@Data
public class UserAccount {
    private Integer id;
    private String username;
    private String password;
    private String role;
}

package wzb.springboot.entity;

import lombok.Data;

@Data
public class Account {
    private Integer id;

    private String username;

    private String password;

    private String role;

    private String token;

    public Account(Integer id, String username, String role, String token) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.token = token;
    }
}

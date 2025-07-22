package wzb.springboot.entity;

import lombok.Data;

@Data
public class Account {
    private Integer id;

    private String username;

    private String password;

    private String role;

    private String token;

    private String avatar;

    public Account(Integer id, String username, String role, String avatar, String token) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.avatar = avatar;
        this.token = token;
    }
}

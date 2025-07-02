package wzb.springboot.entity;

public class Account {
    private Integer id;

    private String username;

    private String role;

    private String token;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Account(Integer id, String username, String role, String token) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.token = token;
    }
}

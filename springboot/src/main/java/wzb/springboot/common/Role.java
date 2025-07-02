package wzb.springboot.common;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    public final String role;

    Role(String role) {
        this.role = role;
    }
}

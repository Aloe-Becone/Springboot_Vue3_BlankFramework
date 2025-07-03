package wzb.springboot.entity;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private String role;

    private String name;

    private String school;

    private String number;

    private String major;

    private String grade;

    private String phone;

    private String info;

    private String sex;

}

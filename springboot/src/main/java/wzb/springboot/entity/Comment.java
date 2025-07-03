package wzb.springboot.entity;

import lombok.Data;

@Data
public class Comment {
    private Integer id;

    private Integer userId;

    private Integer postId;

    private String content;

    private Boolean show;
}

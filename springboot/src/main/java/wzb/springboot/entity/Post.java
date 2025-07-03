package wzb.springboot.entity;

import lombok.Data;

@Data
public class Post {
    private Integer id;

    private Integer userId;

    private String title;

    private String content;

    private String images;

    private Integer likeCount = 0;
}

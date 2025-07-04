package wzb.springboot.entity;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.util.List;

@Data
public class Post {
    private Integer id;

    private Integer userId;

    private String title;

    private String content;

    // 以List接收
    private List<String> imagesList;

    // 以JSON存储
    private String images;

    private Integer likeCount = 0;


}

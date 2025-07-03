package wzb.springboot.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 通用请求封装类
 * 适用场景：前端传递简单键值对/JSON对象
 */

@Data
public class CommonRequest{
    // 基础类型字段
    private Integer integerValue;
    private String stringValue;
    private Long longValue;
    private Boolean boolValue;
    private Double doubleValue;
    private Object data;       // 自由格式数据

}

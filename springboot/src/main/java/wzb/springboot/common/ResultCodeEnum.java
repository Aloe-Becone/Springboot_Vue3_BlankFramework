package wzb.springboot.common;

public enum ResultCodeEnum {
    SUCCESS("200", "成功"),

    PARAM_ERROR("400", "参数异常"),

    SYSTEM_ERROR("500", "系统异常"),

    ;

    public final String code;
    public final String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

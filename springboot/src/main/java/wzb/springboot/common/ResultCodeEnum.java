package wzb.springboot.common;

public enum ResultCodeEnum {
    SUCCESS("200", "成功"),

    PARAM_ERROR("400", "参数异常"),
    TOKEN_INVALID_ERROR("401", "无效的token"),
    TOKEN_CHECK_ERROR("402", "token验证失败，请重新登录"),
    PARAM_LOST_ERROR("403", "参数缺失"),

    SYSTEM_ERROR("500", "系统异常"),
    USER_DONT_EXIT("501", "用户不存在"),
    USER_HAD_EXIT("502", "用户已存在"),
    PWD_ERROR("503", "密码错误"),
    ;

    public final String code;
    public final String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

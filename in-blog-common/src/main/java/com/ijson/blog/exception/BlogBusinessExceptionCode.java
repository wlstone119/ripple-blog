package com.ijson.blog.exception;

/**
 * desc:
 * version: 6.7
 * Created by cuiyongxu on 2019/7/28 9:17 PM
 */
public enum BlogBusinessExceptionCode {

    SYSTEM_ERROR(500_000_001, "系统发生未知错误,该异常已周知管理员,请尝试正常访问网站"),
    REQUEST_WAY_ERROR(500_000_002, "请求方式异常,请检查"),


    USER_NOT_FOUND(200_000_001, "用户不存在或密码不正确,请检查"),
    PASSWORD_NOT_FOUND(200_000_002, "密码验证失败,请检查"),
    INVALID_CURRENT_PASSWORD(200_000_003, "密码不正确,请检查"),
    INFORMATION_IS_INCOMPLETE(200_000_004, "信息不完整,请检查"),
    THE_PASSWORD_CANNOT_BE_EMPTY(200_000_005, "密码不能为空,请检查"),
    USER_ALREADY_EXISTS(200_000_006, "用户已存在,请检查"),
    TITLE_NOT_SET(200_000_007, "未填写文章标题"),
    CONTEXT_NOT_SET(200_000_008, "未填写文章内容"),
    BLOG_NOT_FOUND(200_000_009, "文章未找到"),
    YOU_ALREADY_SUPPORTED_IT(200_000_010, "您已经支持过了"),
    POST_UPDATE_ID_NOT_FOUND(200_000_011, "数据更新必填参数为空"),
    COMMENT_CREATION_FAILED(200_000_012, "评论创建失败"),
    USER_INFORMATION_ACQUISITION_FAILED(200_000_013, "用户信息获取失败,请重新登录"),
    CAPTCHA_ERROR_OR_NOT_PRESENT(200_000_014, "验证码错误或不存在"),
    USER_ENAME_CANNOT_BE_EMPTY(200_000_015, "用户名不能为空"),
    USER_CNAME_CANNOT_BE_EMPTY(200_000_016, "昵称不能为空"),
    USER_EMAIL_CANNOT_BE_EMPTY(200_000_017, "邮箱不能为空"),
    USER_PAASWORD_CANNOT_BE_EMPTY(200_000_018, "密码不能为空"),
    SENSITIVE_TEXT_EXISTS_PLEASE_CHECK_AND_RESUBMIT(200_000_019, "存在敏感文字,请检查后重新提交"),
    TAG_NOT_FOUND(200_000_020, "标签未找到"),
    COMMENTS_SHOULD_NOT_EXCEED_300_WORDS(200_000_021, "评论内容不能超过300字"),
    INCORRECT_ACCOUNT_FORMAT(200_000_022, "账号格式不正确,请字母开头5-16位,允许字母数字下划线"),
    NICKNAME_FORMAT_INCORRECT(200_000_023, "昵称格式不正确,允许中文,英文,数字"),
    INCORRECT_MAILBOX_FORMAT(200_000_024, "邮箱格式不正确"),
    USER_NAMES_MUST_NOT_EXCEED_30_DIGITS(200_000_025, "用户名长度不能超过30位"),
    NICKNAME_MUST_NOT_EXCEED_20_DIGITS(200_000_026, "昵称长度不能超过20位");

    private int code;
    private String message;

    BlogBusinessExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static BlogBusinessExceptionCode valueOf(int errorCode) {
        BlogBusinessExceptionCode[] codes = BlogBusinessExceptionCode.values();
        for (BlogBusinessExceptionCode code : codes) {
            if (code.getCode() == errorCode) {
                return code;
            }
        }

        return null;
    }
}

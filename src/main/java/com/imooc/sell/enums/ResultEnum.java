package com.imooc.sell.enums;

import lombok.Getter;


@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),

    PARAM_ERROR(1, "参数不正确"),

    LOGIN_FAIL(2, "登录失败, 登录信息不正确"),

    LOGOUT_SUCCESS(3, "登出成功"),

    REGISTER_FAIL(4, "注册失败，该账号已被注册"),

    AVATAR_STORE_FAIL(5,"注册失败，头像保存到服务器失败"),

    USER_NOT_FOUND(6,"未找到该用户"),

    CREATE_PROJECT_MASTER_FAIL(7,"创建主订单失败，请检查用户是否存在。"),

    PROJECT_MASTER_NOT_FOUND_BY_USER_ID(8, "未能通过该用户查询到相应的主订单"),

    PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID(9, "未能通过订单ID查询到相应订单"),

    PROJECT_MASTER_USER_ID_NOT_EQUAL(10, "输入的用户ID未能与该订单的用户ID匹配"),

    PROJECT_MASTER_TYPE_ILLEGAL(11, "不合法的项目类型"),

    PROJECT_MASTER_CHANGE_TYPE_FAIL(12, "修改项目类型失败，请检查用户是否存在。"),

    PROJECT_ID_NOT_FOUND(13,"未查询到该项目ID"),

    FAVORITES_EXISTED(14,"该项目已被该用户收藏"),

    FAVORITES_NOT_EXISTED(15, "该项目未被用户收藏"),

    LEAVEMESSAGE_NOT_FOUND(16,"留言未被找到"),

    CREATE_FAILED(17,"创建失败"),

    CHANGE_FAILED(18,"修改失败"),

    FILE_EMPTY(19,"文件为空"),

    ERROR_PASSWORD(20,"密码错误"),

    TAG_NOT_FOUND(21,"标签不存在"),

    SAME_OPENID(22,"用户openID相同"),

    REPEAT_FOLLOW(23,"重复关注"),

    CIRCLE_NOT_FOUND(24,"圈子不存在"),

    CIRCLE_HAD_EXIST(25,"圈子已经存在"),

    REPEAT_JOIN_CIRCLE(26,"重复加入圈子"),

    USERINFO_OR_PROJECTINFO_NOT_MATCH(27,"用户信息或项目信息不匹配")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

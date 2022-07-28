package com.ycicic.fivehearts.framework.security;

import lombok.Data;

/**
 * 用户登录对象
 *
 * @author ycicic
 */
@Data
public class LoginBody {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;

    private Channel channel;

    public enum Channel {
        /**
         * web管理端
         */
        WEB,
        /**
         * 用户端
         */
        APP
    }
}

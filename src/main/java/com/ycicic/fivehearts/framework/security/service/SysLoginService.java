package com.ycicic.fivehearts.framework.security.service;

import javax.annotation.Resource;

import com.ycicic.fivehearts.framework.manager.AsyncManager;
import com.ycicic.fivehearts.framework.manager.factory.AsyncFactory;
import com.ycicic.fivehearts.framework.security.LoginBody;
import com.ycicic.fivehearts.framework.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.ycicic.fivehearts.common.constant.CacheConstants;
import com.ycicic.fivehearts.common.constant.Constants;
import com.ycicic.fivehearts.common.exception.ServiceException;
import com.ycicic.fivehearts.common.exception.user.CaptchaException;
import com.ycicic.fivehearts.common.exception.user.CaptchaExpireException;
import com.ycicic.fivehearts.common.exception.user.UserPasswordNotMatchException;
import com.ycicic.fivehearts.common.utils.DateUtils;
import com.ycicic.fivehearts.common.utils.MessageUtils;
import com.ycicic.fivehearts.common.utils.ServletUtils;
import com.ycicic.fivehearts.common.utils.StringUtils;
import com.ycicic.fivehearts.common.utils.ip.IpUtils;
import com.ycicic.fivehearts.framework.redis.RedisCache;
import com.ycicic.fivehearts.project.system.domain.SysUser;
import com.ycicic.fivehearts.project.system.service.ISysConfigService;
import com.ycicic.fivehearts.project.system.service.ISysUserService;

/**
 * 登录校验方法
 *
 * @author ycicic
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    /**
     * 登录验证
     *
     * @param loginBody loginBody
     * @return 结果
     */
    public String login(LoginBody loginBody) {
        LoginBody.Channel channel = loginBody.getChannel();
        String username = loginBody.getUsername();
        String password = loginBody.getPassword();
        switch (channel) {
            case WEB: {
                boolean captchaOnOff = configService.selectCaptchaOnOff();
                // 验证码开关
                if (captchaOnOff) {
                    validateCaptcha(username, loginBody.getCode(), loginBody.getUuid());
                }
                return getToken(username, password, channel);
            }
            case APP: {
                return getToken(username, password, channel);
            }
            default: {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.channel")));
                throw new UserPasswordNotMatchException();
            }
        }
    }

    private String getToken(String username, String password, LoginBody.Channel channel) {
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        if (LoginBody.Channel.APP.equals(channel)) {
            return tokenService.createAppToken(loginUser);
        }
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
}

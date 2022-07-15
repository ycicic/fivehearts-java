package com.ycicic.fivehearts.project.system.controller;

import com.ycicic.fivehearts.framework.security.RegisterBody;
import com.ycicic.fivehearts.framework.security.service.SysRegisterService;
import com.ycicic.fivehearts.framework.web.controller.BaseController;
import com.ycicic.fivehearts.framework.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ycicic.fivehearts.common.utils.StringUtils;
import com.ycicic.fivehearts.project.system.service.ISysConfigService;

/**
 * 注册验证
 * 
 * @author ycicic
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}

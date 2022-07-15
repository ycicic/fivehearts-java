package com.ycicic.fivehearts.project.system.controller;

import com.ycicic.fivehearts.framework.config.FiveHeartsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ycicic.fivehearts.common.utils.StringUtils;

/**
 * 首页
 *
 * @author ycicic
 */
@RestController
public class SysIndexController
{
    /** 系统基础配置 */
    @Autowired
    private FiveHeartsConfig fiveheartsConfig;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index()
    {
        return StringUtils.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", fiveheartsConfig.getName(), fiveheartsConfig.getVersion());
    }
}

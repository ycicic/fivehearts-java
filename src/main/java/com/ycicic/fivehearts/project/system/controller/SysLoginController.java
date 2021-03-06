package com.ycicic.fivehearts.project.system.controller;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson2.JSONObject;
import com.ycicic.fivehearts.framework.security.LoginBody;
import com.ycicic.fivehearts.framework.security.service.SysLoginService;
import com.ycicic.fivehearts.framework.security.service.SysPermissionService;
import com.ycicic.fivehearts.framework.web.domain.AjaxResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ycicic.fivehearts.common.constant.Constants;
import com.ycicic.fivehearts.common.utils.SecurityUtils;
import com.ycicic.fivehearts.project.system.domain.SysMenu;
import com.ycicic.fivehearts.project.system.domain.SysUser;
import com.ycicic.fivehearts.project.system.service.ISysMenuService;

/**
 * 登录验证
 *
 * @author ycicic
 */
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @SneakyThrows
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        String token = loginService.login(loginBody);
        ajax.put(Constants.TOKEN, token);
        ajax.put(AjaxResult.DATA_TAG, JSONObject.parseObject("{\"token\":\"" + token + "\"}"));
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}

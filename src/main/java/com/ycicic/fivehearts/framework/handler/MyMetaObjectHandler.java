package com.ycicic.fivehearts.framework.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ycicic.fivehearts.common.utils.SecurityUtils;
import com.ycicic.fivehearts.framework.security.LoginUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ycicic
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        strictInsertFill(metaObject, "createBy", String.class, loginUser.getUsername());
        strictInsertFill(metaObject, "createTime", Date.class, new Date());
        strictInsertFill(metaObject, "updateBy", String.class, loginUser.getUsername());
        strictInsertFill(metaObject, "updateTime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        strictUpdateFill(metaObject, "updateBy", String.class, loginUser.getUsername());
        strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
}

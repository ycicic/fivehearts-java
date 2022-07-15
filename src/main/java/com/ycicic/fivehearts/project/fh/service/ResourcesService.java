package com.ycicic.fivehearts.project.fh.service;

import java.util.List;
import com.ycicic.fivehearts.project.fh.domain.Resources;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 资源管理Service接口
 * 
 * @author ycicic
 * @date 2022-07-14
 */
public interface ResourcesService extends IService<Resources> {

    /**
    * Resources 分页查询
    * @param resources resources
    * @param current 当前页码
    * @param size 每页显示记录数
    * @return 分页查询结果
    */
    IPage<Resources> queryPage(Resources resources, Long current, Long size);

    /**
    * Resources 列表查询
    * @param resources resources
    * @return 列表查询结果
    */
    List<Resources> queryList(Resources resources);

}

package com.ycicic.fivehearts.project.fh.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import com.ycicic.fivehearts.project.fh.mapper.ResourcesMapper;
import com.ycicic.fivehearts.project.fh.domain.Resources;
import com.ycicic.fivehearts.project.fh.service.ResourcesService;

/**
 * 资源管理Service业务层处理
 * 
 * @author ycicic
 * @date 2022-07-14
 */
@Service
public class ResourcesServiceImpl extends ServiceImpl<ResourcesMapper,Resources> implements ResourcesService {

    @Override
    public IPage<Resources> queryPage(Resources resources, Long current, Long size) {
        return page(
                new Page<>(current, size),
                resources.getLambdaQueryWrapper()
        );
    }

    @Override
    public List<Resources> queryList(Resources resources){
        return list(resources.getLambdaQueryWrapper());
    }

}

package com.ycicic.fivehearts.project.fh.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ycicic.fivehearts.framework.aspectj.lang.annotation.Excel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycicic.fivehearts.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

import lombok.EqualsAndHashCode;

/**
 * 资源管理对象 fh_resources
 *
 * @author ycicic
 * @date 2022-07-14
 */
@Data
@TableName("fh_resources")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "资源管理对象 fh_resources")
public class Resources extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 资源类型
     */
    @Excel(name = "资源类型")
    @ApiModelProperty("资源类型")
    private Integer type;

    /**
     * 名称
     */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /**
     * 资源详情
     */
    @Excel(name = "资源详情")
    @ApiModelProperty("资源详情")
    private String info;


    public LambdaQueryWrapper<Resources> getLambdaQueryWrapper() {
        LambdaQueryWrapper<Resources> wrapper = new QueryWrapper<Resources>().lambda();
        wrapper.eq(Objects.nonNull(type), Resources::getType, type);
        wrapper.like(Objects.nonNull(name), Resources::getName, "%" + name + "%");
        return wrapper;
    }

}

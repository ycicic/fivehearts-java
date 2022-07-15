package com.ycicic.fivehearts.project.fh.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ycicic.fivehearts.framework.aspectj.lang.annotation.Log;
import com.ycicic.fivehearts.framework.aspectj.lang.enums.BusinessType;
import com.ycicic.fivehearts.project.fh.domain.Resources;
import com.ycicic.fivehearts.project.fh.service.ResourcesService;
import com.ycicic.fivehearts.common.utils.poi.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 资源管理Controller
 *
 * @author ycicic
 * @date 2022-07-14
 */
@Api(tags = "资源管理Controller")
@RestController
@RequestMapping("/fh/resources")
public class ResourcesController {

    private ResourcesService resourcesService;

    @Autowired
    private void setResourcesService(ResourcesService resourcesService) {
        this.resourcesService = resourcesService;
    }

    /**
     * 查询资源管理列表
     */
    @PreAuthorize("@ss.hasPermi('fh:resources:list')")
    @GetMapping("/list")
    @ApiOperation("查询资源管理列表")
    public IPage<Resources> list(@RequestParam(value = "current",defaultValue = "1") Long current,
                      @RequestParam(value = "size",defaultValue = "10") Long size,
                      Resources resources) {
        return resourcesService.queryPage(resources, current, size);
    }

    /**
     * 导出资源管理列表
     */
    @PreAuthorize("@ss.hasPermi('fh:resources:export')")
    @Log(title = "资源管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出资源管理列表")
    public void export(HttpServletResponse response, Resources resources) {
        List<Resources> list = resourcesService.queryList(resources);
        ExcelUtil<Resources> util = new ExcelUtil<>(Resources.class);
        util.exportExcel(response, list, "资源管理数据");
    }

    /**
     * 获取资源管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fh:resources:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取资源管理详细信息")
    public Resources getInfo(@PathVariable("id") Long id) {
        return resourcesService.getById(id);
    }

    /**
     * 新增资源管理
     */
    @PreAuthorize("@ss.hasPermi('fh:resources:add')")
    @Log(title = "资源管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增资源管理")
    public void add(@RequestBody Resources resources) {
        resourcesService.save(resources);
    }

    /**
     * 修改资源管理
     */
    @PreAuthorize("@ss.hasPermi('fh:resources:edit')")
    @Log(title = "资源管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改资源管理")
    public void edit(@RequestBody Resources resources) {
        resourcesService.updateById(resources);
    }

    /**
     * 删除资源管理
     */
    @PreAuthorize("@ss.hasPermi('fh:resources:remove')")
    @Log(title = "资源管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation("删除资源管理")
    public void remove(@PathVariable Long[] ids) {
        resourcesService.removeByIds(Arrays.asList(ids));
    }
}

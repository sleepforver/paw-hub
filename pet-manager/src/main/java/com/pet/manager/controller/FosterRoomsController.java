package com.pet.manager.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import com.pet.manager.domain.vo.FosterRoomsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pet.common.annotation.Log;
import com.pet.common.core.controller.BaseController;
import com.pet.common.core.domain.AjaxResult;
import com.pet.common.enums.BusinessType;
import com.pet.manager.domain.FosterRooms;
import com.pet.manager.service.IFosterRoomsService;
import com.pet.common.utils.poi.ExcelUtil;
import com.pet.common.core.domain.R;
import com.pet.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 寄养房间信息Controller
 *
 * @author kkk
 * @date 2025-02-17
 */
@Api(tags = "寄养房间信息Controller")
@RestController
@RequestMapping("/manager/rooms")
public class FosterRoomsController extends BaseController
{
    @Autowired
    private IFosterRoomsService fosterRoomsService;

/**
 * 查询寄养房间信息列表
 */
@ApiOperation("查询寄养房间信息列表")
@PreAuthorize("@ss.hasPermi('manager:rooms:list')")
@GetMapping("/list")
    public TableDataInfo list(FosterRooms fosterRooms)
    {
        startPage();
        List<FosterRooms> list = fosterRoomsService.selectFosterRoomsList(fosterRooms);
        return getDataTable(list);
    }

    /**
     * 导出寄养房间信息列表
     */
    @ApiOperation("导出寄养房间信息列表")
    @PreAuthorize("@ss.hasPermi('manager:rooms:export')")
    @Log(title = "寄养房间信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FosterRooms fosterRooms)
    {
        List<FosterRooms> list = fosterRoomsService.selectFosterRoomsList(fosterRooms);
        ExcelUtil<FosterRooms> util = new ExcelUtil<FosterRooms>(FosterRooms.class);
        util.exportExcel(response, list, "寄养房间信息数据");
    }

    /**
     * 导入寄养房间信息列表
     */
    @ApiOperation("导入商品管理列表")
    @PreAuthorize("@ss.hasPermi('manage:rooms:add')")
    @Log(title = "商品管理", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult excelImport(MultipartFile file) throws Exception{
        ExcelUtil<FosterRooms> util = new ExcelUtil<FosterRooms>(FosterRooms.class);
        List<FosterRooms> fosterRoomsList = util.importEasyExcel(file.getInputStream());
        return toAjax(fosterRoomsService.insertSkuBatch(fosterRoomsList));
    }

    /**
     * 获取寄养房间信息详细信息
     */
    @ApiOperation("获取寄养房间信息详细信息")
    @PreAuthorize("@ss.hasPermi('manager:rooms:query')")
    @GetMapping(value = "/{fosterRoomId}")
    public R<FosterRooms> getInfo(@PathVariable("fosterRoomId") Long fosterRoomId)
    {
        return R.ok(fosterRoomsService.selectFosterRoomsByFosterRoomId(fosterRoomId));
    }

    /**
     * 新增寄养房间信息
     */
    @ApiOperation("新增寄养房间信息")
    @PreAuthorize("@ss.hasPermi('manager:rooms:add')")
    @Log(title = "寄养房间信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FosterRooms fosterRooms)
    {
        return toAjax(fosterRoomsService.insertFosterRooms(fosterRooms));
    }

    /**
     * 修改寄养房间信息
     */
    @ApiOperation("修改寄养房间信息")
    @PreAuthorize("@ss.hasPermi('manager:rooms:edit')")
    @Log(title = "寄养房间信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FosterRooms fosterRooms)
    {
        return toAjax(fosterRoomsService.updateFosterRooms(fosterRooms));
    }

    /**
     * 删除寄养房间信息
     */
    @ApiOperation("删除寄养房间信息")
    @PreAuthorize("@ss.hasPermi('manager:rooms:remove')")
    @Log(title = "寄养房间信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{fosterRoomIds}")
    public AjaxResult remove(@PathVariable Long[] fosterRoomIds)
    {
        return toAjax(fosterRoomsService.deleteFosterRoomsByFosterRoomIds(fosterRoomIds));
    }

    /**
     * 根据房间号来查询房间信息
     */
}

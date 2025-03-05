package com.pet.manager.domain;

        import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
        import com.alibaba.excel.annotation.ExcelProperty;
        import com.alibaba.excel.annotation.write.style.ColumnWidth;
        import com.alibaba.excel.annotation.write.style.HeadFontStyle;
        import com.alibaba.excel.annotation.write.style.HeadRowHeight;
        import com.pet.common.annotation.Excel;
            import com.pet.common.core.domain.BaseEntity;
    import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 寄养房间信息对象 tb_foster_rooms
 *
 * @author kkk
 * @date 2025-02-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ExcelIgnoreUnannotated //注解表示在导出excel时，忽略没有被任何注解标注的字段
@ColumnWidth(16) //注解用于设置列的宽度
@HeadRowHeight(14) //注解用于设置表头行的高度
@HeadFontStyle(fontHeightInPoints = 11) //注解用于设置表头行的字体样式
public class FosterRooms extends BaseEntity
        {   private static final long serialVersionUID = 1L;
                    /** 寄养房间ID */

        private Long fosterRoomId;

                /** 房间号 */
                            @Excel(name = "房间号")
                            @ExcelProperty(value = "房间号")
        private Long roomNumber;

                            /** 员工id */
                            @Excel(name = "员工id")
        private Long empId;

                /** 房间照片 */
                            @Excel(name = "房间照片")
                            @ExcelProperty(value = "房间照片")
        private String roomImage;

                /** 房型 */
                            @Excel(name = "房型")
                            @ExcelProperty(value = "房型")
        private String roomType;

                /** 品牌 */
                            @Excel(name = "品牌")
                            @ExcelProperty(value = "品牌")
        private String brandName;

                /** 单间最大宠物数量 */

        private Long maxPetsPerRoom;

                /** 当前房间宠物数量 */

        private Long currentPetsCount;

                /** 房间是否可用 */
                            @Excel(name = "房间是否可用")
                            @ExcelProperty(value = "房间是否可用")
        private Integer isAvailable;




}

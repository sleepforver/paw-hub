package com.pet.manager.domain;

        import com.pet.common.annotation.Excel;
            import com.pet.common.core.domain.BaseEntity;
    import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 寄养房间类型对象 tb_foster_room_types
 *
 * @author kkk
 * @date 2025-02-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrTypes extends BaseEntity
        {   private static final long serialVersionUID = 1L;
                    /** 房间类型ID，主键，自增 */
                            @Excel(name = "房间类型ID，主键，自增")
        private Long roomTypeId;

                /** 房间类型 */
                            @Excel(name = "房间类型")
        private String roomType;

                /** 房间号 */
                            @Excel(name = "房间号")
        private Long roomNumber;

                /** 单间最大宠物数量 */
                            @Excel(name = "单间最大宠物数量")
        private Long maxPetsPerRoom;

    


}

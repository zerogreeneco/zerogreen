package zerogreen.eco.dto.store;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.userentity.StoreInfo;
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.entity.userentity.UserRole;

@Getter
@Setter
@Builder
public class StoreDto {
    private String storeName;
    private String storeRegNum;
    private StoreType storeType;
    private StoreInfo storeInfo;
    private UserRole userRole;

    private Long id;
    private Long fileId;

    private String username;




}


package zerogreen.eco.dto.store;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.userentity.StoreInfo;
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.entity.userentity.UserRole;

@Getter
@Setter
public class StoreDto {
    private String storeName;
    private String storeRegNum;
    private StoreType storeType;
    private StoreInfo storeInfo;
    private UserRole userRole;

    private Long id;
    private Long fileId;

    private String username;

    //storeMemberServiceImpl에 쓰였음
    public StoreDto(Long id, String username, String storeName, StoreType storeType,
                               StoreInfo storeInfo, String storeRegNum,UserRole userRole) {
        this.storeName = storeName;
        this.storeRegNum = storeRegNum;
        this.storeType = storeType;
        this.storeInfo = storeInfo;
        this.userRole = userRole;
        this.id = id;
        this.username = username;
    }

}


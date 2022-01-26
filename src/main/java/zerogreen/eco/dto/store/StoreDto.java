package zerogreen.eco.dto.store;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.entity.userentity.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class StoreDto {
    private String storeName;
    private String storeRegNum;
    private StoreType storeType;

    private Long id;
    private String username;
    private UserRole userRole;

    private List<StoreImageFile> imageFiles;

    private String postalCode;
    private String storeAddress;
    private String storePhoneNumber;
    private String openTime;
    private String closeTime;

    private List<StoreMenu> menuList;

    private Long menuId;
    private String menuName;
    private int menuPrice;
    private VegetarianGrade vegetarianGrades;



    //storeMemberServiceImpl에 쓰였음
    public StoreDto(String storeName, String storeRegNum, StoreType storeType,
                    Long id, String username, UserRole userRole, List<StoreImageFile> imageFiles,
                    String postalCode, String storeAddress, String storePhoneNumber, String openTime, String closeTime ,
                    List<StoreMenu> menuList ) {
        this.storeName = storeName;
        this.storeRegNum = storeRegNum;
        this.storeType = storeType;
        this.id = id;
        this.username = username;
        this.userRole = userRole;
        this.imageFiles = imageFiles;
        this.postalCode = postalCode;
        this.storeAddress = storeAddress;
        this.storePhoneNumber = storePhoneNumber;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.menuList = menuList;
    }

    public StoreDto(String storeName, String storeRegNum, StoreType storeType, Long id, UserRole userRole, String username, String postalCode, String storeAddress, String storePhoneNumber, LocalDateTime openTime, LocalDateTime closeTime) {
    }
}


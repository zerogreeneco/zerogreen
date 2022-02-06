package zerogreen.eco.dto.store;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.entity.userentity.*;

import java.util.List;

@Getter
@Setter
public class StoreDto {

    private Long id;
    private String username;
    private UserRole userRole;

    private String storeAddress;
    private String storePhoneNumber;
    private String openTime;
    private String closeTime;

    private Long menuId;
    private String menuName;
    private int menuPrice;

    private BasicUser basicUser;

    private StoreMember storeMember;
    private String storeRegNum;

    private VegetarianGrade vegetarianGrades;

    //여기서부터..
    private Long sno;
    private String storeName;

    private StoreType storeType;
    private StoreInfo storeInfo;

    private List<StoreImageFile> imageFiles;
    private List<StoreMenu> menuList;

    private Long count;
    private Long like;

//    public StoreDto(){}

    //이친구...뭐죠..?
    public StoreDto(Long id) {
        this.id = id;
    }

    //index에 승인받은 가게리스트
    public StoreDto(String storeName, StoreType storeType){
        this.storeName = storeName;
        this.storeType = storeType;
    }

    //StoreMemberRespositoryImpl에서 쓰는 생성자 ** 아래 주석포함 작업중 **
    public StoreDto(Long sno, String storeName, StoreType storeType, StoreInfo storeInfo,
                    Long count, Long like) {
        this.sno = sno;
        this.storeName = storeName;
        this.storeType = storeType;
        this.storeInfo = storeInfo;
        this.count = count;
        this.like = like;
    }

    public StoreDto(List<StoreMenu> menuList) {
        this.menuList = menuList;
    }

    //Detail에 Store데이터 가져오기
    @Builder
    public StoreDto(Long sno, String storeName, StoreType storeType, StoreInfo storeInfo) {
        this.sno = sno;
        this.storeName = storeName;
        this.storeType = storeType;
        this.storeInfo = storeInfo;
    }
    //ㄴ이상 예진이 작업중


    //List DTO
    public StoreDto(Long id,String storeName, String storePhoneNumber,
    String openTime, String closeTime) {
        this.id = id;
        this.storeName = storeName;
        this.storePhoneNumber = storePhoneNumber;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public StoreDto(Long id, UserRole userRole, StoreType storeType, String storeName, String storePhoneNumber,
                    String openTime, String closeTime, List<StoreImageFile> imageFiles, List<StoreMenu> menuList) {
        this.id = id;
        this.userRole = userRole;
        this.storeType = storeType;
        this.storeName = storeName;
        this.storePhoneNumber = storePhoneNumber;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.imageFiles = imageFiles;
        this.menuList = menuList;
    }

}


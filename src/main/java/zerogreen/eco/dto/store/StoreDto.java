package zerogreen.eco.dto.store;

import com.sun.istack.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.entity.userentity.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public class StoreDto {

    private Long id;
    private UserRole userRole;
    @NotBlank(message = "가게 연락처를 입력해 주세요")
    private String storePhoneNumber;
    private String openTime;
    private String closeTime;
    private String storeDescription;
    private String socialAddress1, socialAddress2;

    private Long menuId;
    private String menuName;
    private String menuPrice;

    private Long sno;
    private String storeName;

    private StoreType storeType;
    private StoreInfo storeInfo;

    private Long likesCount;
    private Long like;

    private Long reviewCount;

    @Nullable
    private VegetarianGrade vegetarianGrades;

    private List<MultipartFile> uploadFiles;

    private List<StoreImageFile> imageFile;
    private List<StoreDto> imageList = new ArrayList<>();

    private List<StoreMenu> menuList;
    private List<StoreMenuDto> storeMenuList = new ArrayList<>();

    private Long imageId;
    private String fileName;
    private String storeFileName;
    private String thumbnailName;
    private String filePath;
    private String thumbPath;
    private StoreMember storeMember;


    public StoreDto(){}

    //image list
    public StoreDto(StoreImageFile storeImageFile) {
        this.imageId = storeImageFile.getId();
        this.fileName = storeImageFile.getFileName();
        this.storeFileName = storeImageFile.getStoreFileName();
        this.filePath = storeImageFile.getFilePath();
        this.storeMember = storeImageFile.getStoreMember();
        this.thumbnailName = storeImageFile.getThumbnailName();
        this.thumbPath = storeImageFile.getThumbPath();
    }

    //Store db (Detail) qdsl ** 작업중 **
    public StoreDto(Long sno, String storeName, StoreType storeType, StoreInfo storeInfo, List<StoreMenu> menuList,
                    Long likesCount, Long reviewCount) {
        this.sno = sno;
        this.storeName = storeName;
        this.storeType = storeType;
        this.storeInfo = storeInfo;
        this.storeMenuList = menuList.stream().map(StoreMenuDto::new).collect(Collectors.toList());
        this.likesCount = likesCount;
        this.reviewCount = reviewCount;
    }

    //Store db (Detail) orm ** 작업중 **
    public StoreDto(StoreMember storeMember) {
        this.sno = storeMember.getId();
        this.storeName = storeMember.getStoreName();
        this.storeType = storeMember.getStoreType();
        this.storeInfo = storeMember.getStoreInfo();
        this.storeMenuList = storeMember.getMenuList().stream().map(StoreMenuDto::new).collect(Collectors.toList());
        this.imageList = storeMember.getImageFile().stream().map(StoreDto::new).collect(Collectors.toList());
    }

    //Store db (Detail) 기존 ** 작업중 **
    @Builder
    public StoreDto(Long sno, String storeName, StoreType storeType, StoreInfo storeInfo,
                    List<StoreImageFile> imageFile, List<StoreMenu> menuList, Long likesCount, Long reviewCount) {
        this.sno = sno;
        this.storeName = storeName;
        this.storeType = storeType;
        this.storeInfo = storeInfo;
        this.imageFile = imageFile;
        this.menuList = menuList;
        this.likesCount = likesCount;
        this.reviewCount = reviewCount;
    }

    //List DTO
    public StoreDto(Long id,String storeName, String storePhoneNumber,
    String openTime, String closeTime) {
        this.id = id;
        this.storeName = storeName;
        this.storePhoneNumber = storePhoneNumber;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    //StoreUpdate
    public StoreDto(String storeName, StoreType storeType, String storePhoneNumber, String openTime, String closeTime,
                    String storeDescription, String socialAddress1, String socialAddress2){
        this.storeName = storeName;
        this.storeType = storeType;
        this.storePhoneNumber = storePhoneNumber;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.storeDescription = storeDescription;
        this.socialAddress1 = socialAddress1;
        this.socialAddress2 = socialAddress2;
    }

/*
    public StoreDto(Long id, UserRole userRole, StoreType storeType, String storeName, String storePhoneNumber,
                    String openTime, String closeTime, List<StoreImageFile> imageFile, List<StoreMenu> menuList) {
        this.id = id;
        this.userRole = userRole;
        this.storeType = storeType;
        this.storeName = storeName;
        this.storePhoneNumber = storePhoneNumber;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.imageFile = imageFile;
        this.menuList = menuList;
    }
*/

}


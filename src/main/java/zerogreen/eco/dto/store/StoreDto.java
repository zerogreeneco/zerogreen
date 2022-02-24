package zerogreen.eco.dto.store;

import com.sun.istack.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.entity.userentity.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class StoreDto {

    private Long id;
    private String username;
    private UserRole userRole;
    private String storeAddress;
    @NotBlank(message = "가게 연락처를 입력해 주세요")
    private String storePhoneNumber;
    private String openTime;
    private String closeTime;
    private String storeDescription;
    private String socialAddress1, socialAddress2;

    private Long menuId;
    private String menuName;
    private int menuPrice;
    private List<StoreMenu> menuList;

    private BasicUser basicUser;

    private String storeRegNum;
    private List<StoreImageFile> imageFiles;


    //여기서부터..
    private Long sno;
    private String storeName;

    private StoreType storeType;
    private StoreInfo storeInfo;

    private Long count;
    private Long like;

    @Nullable
    private VegetarianGrade vegetarianGrades;

    private List<MultipartFile> uploadFiles;

    private Long imageId;
    private String fileName;
    private String storeFileName;
    private String filePath;
    private StoreMember storeMember;
    private String thumbnailName;


    public StoreDto(){}

    //이친구...뭐죠..?
    public StoreDto(Long id) {
        this.id = id;
    }

    //index에 승인받은 가게리스트
    public StoreDto(String storeName, StoreType storeType){
        this.storeName = storeName;
        this.storeType = storeType;
    }

    //image list (Detail)
    public StoreDto(StoreImageFile storeImageFile) {
        this.imageId = storeImageFile.getId();
        this.fileName = storeImageFile.getFileName();
        this.storeFileName = storeImageFile.getStoreFileName();
        this.filePath = storeImageFile.getFilePath();
        this.storeMember = storeImageFile.getStoreMember();
        this.thumbnailName = "thumb_" + storeImageFile.getThumbnailName();
    }

    //Store db (Detail)
    @Builder
    public StoreDto(Long sno, String storeName, StoreType storeType, StoreInfo storeInfo,
                    Long count) {

        this.sno = sno;
        this.storeName = storeName;
        this.storeType = storeType;
        this.storeInfo = storeInfo;
        this.count = count;
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
}


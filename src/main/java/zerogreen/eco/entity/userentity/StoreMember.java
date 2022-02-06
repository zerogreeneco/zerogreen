package zerogreen.eco.entity.userentity;

import lombok.*;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.file.StoreImageFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@DiscriminatorValue("STORE")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreMember extends BasicUser{

    private String storeName;
    // 사업자 등록번호
    private String storeRegNum;

    //상세페이지 db 뿌릴때 like count할 때 필요함
    private Long count;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @Embedded
    private StoreInfo storeInfo;

    @OneToMany(mappedBy = "storeMember")
    private List<StoreMenu> menuList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = ALL)
    @JoinColumn(name = "reg_file_id")
    private RegisterFile registerFile;

    @OneToMany(mappedBy = "storeMember")
    private List<StoreImageFile> imageFiles = new ArrayList<>();


    // 회원 가입
    @Builder
    public StoreMember(String username, String phoneNumber, String password, UserRole userRole,
                       String storeName, String storeRegNum, StoreType storeType, String postalCode, String storeAddress,
                       String storeDetailAddress, String storePhoneNumber, RegisterFile registerFile) {

        super(username, phoneNumber, password, userRole);
        this.storeName = storeName;
        this.storeRegNum = storeRegNum;
        this.storeType = storeType;
        this.storeInfo = new StoreInfo(postalCode, storeAddress, storeDetailAddress, storePhoneNumber);
        this.setRegisterFile(new RegisterFile(registerFile.getUploadFileName(), registerFile.getStoreFileName(), registerFile.getFilePath()));
    }

    //회원가입 테스트데이터용, 예진이
    //@Builder(builderMethodName = "testBuilder")
/*
    public StoreMember(String username, String phoneNumber, String password, UserRole userRole,
                       String storeName, String storeRegNum, StoreType storeType, String postalCode, String storeAddress,
                       String storeDetailAddress, String storePhoneNumber, String storeDescription, String socialAddress1,
                       String socialAddress2, String openTime, String closeTime, RegisterFile registerFile) {

        super(username, phoneNumber, password, userRole);
        this.storeName = storeName;
        this.storeRegNum = storeRegNum;
        this.storeType = storeType;
        this.storeInfo = new StoreInfo(postalCode, storeAddress, storeDetailAddress, storePhoneNumber, storeDescription,
                socialAddress1, socialAddress2, openTime, closeTime);
        this.setRegisterFile(new RegisterFile(registerFile.getUploadFileName(), registerFile.getStoreFileName(), registerFile.getFilePath()));
    }
*/

    // DTO -> 엔티티
    public StoreMember(String username, String phoneNumber, String password, UserRole userRole, String storeName,
                       String storeRegNum, StoreType storeType, String storeAddress,
                       String storeDetailAddress,String storePhoneNumber, String postalCode) {
        super(username, phoneNumber, password, userRole);
        this.storeName = storeName;
        this.storeRegNum = storeRegNum;
        this.storeType = storeType;
        this.storeInfo = new StoreInfo(postalCode, storeAddress, storeDetailAddress,storePhoneNumber);
//        new StoreInfo(postalCode, storeAddress, storeDetailAddress, storePhoneNumber);
    }

    // Test 데이터 용 (삭제 예정)
    public StoreMember(String username, String phoneNumber, String password, UserRole userRole,
                       String storeRegNum, StoreType storeType) {

        super(username, phoneNumber, password, userRole);
        this.storeRegNum = storeRegNum;
        this.storeType = storeType;
    }

    // 가게 정보 등록
    public StoreMember(String storeAddress, String storePhoneNumber, String storeDescription,
                        String openTime, String closeTime) {
        storeInfo = new StoreInfo(storeAddress, storePhoneNumber, storeDescription, openTime, closeTime);
    }

}

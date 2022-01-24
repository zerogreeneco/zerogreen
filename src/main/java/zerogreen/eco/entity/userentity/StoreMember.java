package zerogreen.eco.entity.userentity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.file.StoreImageFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@DiscriminatorValue("STORE")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreMember extends BasicUser{

    private String storeName;
    private String storeRegNum;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @Embedded
    private StoreInfo storeInfo;

    @OneToMany(mappedBy = "storeMember")
    private List<StoreMenu> menuList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = ALL)
    @JoinColumn(name = "reg_file_id")
    private RegisterFile registerFile;

    @OneToMany(mappedBy = "storeMember", cascade = ALL)
    private List<StoreImageFile> imageFiles = new ArrayList<>();

    // 회원 가입
    public StoreMember(String username, String phoneNumber, String password, UserRole userRole,
                       String storeName, String storeRegNum, StoreType storeType, String storeAddress, String storePhoneNumber, RegisterFile registerFile, String postalCode) {

        super(username, phoneNumber, password, userRole);
        this.storeName = storeName;
        this.storeRegNum = storeRegNum;
        this.storeType = storeType;
        this.storeInfo =  new StoreInfo(postalCode, storeAddress, storePhoneNumber);
        this.setRegisterFile(new RegisterFile(registerFile.getUploadFileName(), registerFile.getStoreFileName(), registerFile.getFilePath()));
    }

    // DTO -> 엔티티
    public StoreMember(String username, String phoneNumber, String password, UserRole userRole, String storeName,
                       String storeRegNum, StoreType storeType, String storeAddress, String storePhoneNumber, String postalCode) {
        super(username, phoneNumber, password, userRole);
        this.storeName = storeName;
        this.storeRegNum = storeRegNum;
        this.storeType = storeType;
        this.storeInfo = new StoreInfo(postalCode, storeAddress, storePhoneNumber);
        new StoreInfo(postalCode, storeAddress, storePhoneNumber);
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
                        LocalDateTime openTime, LocalDateTime closeTime) {
        storeInfo = new StoreInfo(storeAddress, storePhoneNumber, storeDescription, openTime, closeTime);
    }
}

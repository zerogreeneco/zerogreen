package zerogreen.eco.entity.userentity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("STORE")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreMember extends BasicUser{

    private String storeRegNum;
    private String storeType;

    @Embedded
    private StoreInfo storeInfo;


    @OneToMany(mappedBy = "storeMember")
    private List<StoreMenu> menuList = new ArrayList<>();

    // 회원 가입
    public StoreMember(String username, String nickname, String phoneNumber, String password, UserRole userRole,
                       String storeRegNum, String storeType) {
        super(username, nickname, phoneNumber, password, userRole);
        this.storeRegNum = storeRegNum;
        this.storeType = storeType;
    }

    // 가게 정보 등록
    public StoreMember(String storeName, String storeAddress, String storePhoneNumber,
                        LocalDateTime openTime, LocalDateTime closeTime) {
        storeInfo = new StoreInfo(storeName, storeAddress, storePhoneNumber, openTime, closeTime);
    }
}

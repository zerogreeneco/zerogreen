package zerogreen.eco.entity.userentity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("ECO")
@Table(name = "ECO_STORE")
public class EcoStoreUser extends User {

    private String storeRegNum;

    @Embedded
    private StoreInfo storeInfo;

    @OneToMany(mappedBy = "ecoStore")
    private List<StoreMenu> menuList = new ArrayList<>();

    // 회원 가입
    public EcoStoreUser(String username, String nickname, String phoneNumber, String password) {
        super(username, nickname, phoneNumber, password);
    }

    // 가게 정보 등록
    public EcoStoreUser(String storeRegNum, String storeName, String storeAddress, String storePhoneNumber,
                        LocalDateTime openTime, LocalDateTime closeTime) {
        this.storeRegNum = storeRegNum;
        storeInfo = new StoreInfo(storeName, storeAddress, storePhoneNumber, openTime, closeTime);
    }
}

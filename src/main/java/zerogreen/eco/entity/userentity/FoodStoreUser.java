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
@Getter
@Setter
@DiscriminatorValue("FOOD")
@Table(name = "FOOD_STORE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodStoreUser extends BasicUser {

    private String storeRegNum;

    @Embedded
    private StoreInfo storeInfo;

    @OneToMany(mappedBy = "foodStore")
    private List<StoreMenu> menuList = new ArrayList<>();

    public FoodStoreUser(String username, String nickname, String phoneNumber, String password, UserRole userRole, String storeRegNum) {
        super(username, nickname, phoneNumber, password, userRole);
        this.storeRegNum = storeRegNum;
    }

    // 가게 정보 등록
    public FoodStoreUser(String storeName, String storeAddress, String storePhoneNumber,
                        LocalDateTime openTime, LocalDateTime closeTime) {
        storeInfo = new StoreInfo(storeName, storeAddress, storePhoneNumber, openTime, closeTime);
    }
}

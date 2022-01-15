package zerogreen.eco.entity.userentity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("FOOD")
@Table(name = "FOOD_STORE")
public class FoodStoreUser extends BasicUser {

    private String storeRegNum;

    @Embedded
    private StoreInfo storeInfo;

    @OneToMany(mappedBy = "foodStore")
    private List<StoreMenu> menuList = new ArrayList<>();

    public FoodStoreUser(String username, String nickname, String phoneNumber, String password) {
        super(username, nickname, phoneNumber, password);
    }
}

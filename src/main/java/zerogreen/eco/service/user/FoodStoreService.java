package zerogreen.eco.service.user;

import zerogreen.eco.entity.userentity.EcoStoreUser;
import zerogreen.eco.entity.userentity.FoodStoreUser;

public interface FoodStoreService {

    public Long save(FoodStoreUser foodStore);

    public void storeInfoSave(FoodStoreUser foodStore);

}

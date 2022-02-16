package zerogreen.eco.service.store;

import zerogreen.eco.dto.store.StoreMenuDto;
import zerogreen.eco.entity.userentity.StoreMenu;
import zerogreen.eco.entity.userentity.VegetarianGrade;

public interface StoreMenuService {

//    void updateStoreMenu(Long id,StoreMenuDto storeMenuDto);
    void updateStoreMenu(Long id, String menuName, int menuPrice, VegetarianGrade vegetarianGrade);
}
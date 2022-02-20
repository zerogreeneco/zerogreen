package zerogreen.eco.service.store;

import zerogreen.eco.dto.store.StoreMenuDto;
import zerogreen.eco.entity.userentity.StoreMenu;
import zerogreen.eco.entity.userentity.VegetarianGrade;

import java.util.List;

public interface StoreMenuService {

    void updateStoreMenu(Long id, String menuName, int menuPrice, VegetarianGrade vegetarianGrade);
    void updateStoreMenu(Long id, String menuName, int menuPrice);

    List<StoreMenuDto> getStoreMenu(Long id);

    void deleteMenu(Long id);

    //상세페이지 리스트
    List<StoreMenuDto> getMenuByStore(Long sno);
    //save test data
    Long saveStoreMenuTest(StoreMenu storeMenu);

}
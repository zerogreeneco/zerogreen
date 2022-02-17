package zerogreen.eco.service.store;

import zerogreen.eco.dto.store.StoreMenuDto;
import zerogreen.eco.entity.userentity.VegetarianGrade;

import java.util.List;

public interface StoreMenuService {

    void updateStoreMenu(Long id, String menuName, int menuPrice, VegetarianGrade vegetarianGrade);

    List<StoreMenuDto> getStoreMenu(Long id);

    void menuDelete(Long id);
}
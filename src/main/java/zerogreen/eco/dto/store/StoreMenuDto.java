package zerogreen.eco.dto.store;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import zerogreen.eco.entity.userentity.StoreMenu;
import zerogreen.eco.entity.userentity.VegetarianGrade;

@Setter
@Getter
@Slf4j
public class StoreMenuDto {

    private Long id;
    private String menuName;
    private String menuPrice;
    private VegetarianGrade vegetarianGrade;


    public StoreMenuDto(){}

    public StoreMenuDto(String menuName, String menuPrice, VegetarianGrade vegetarianGrade){
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.vegetarianGrade = vegetarianGrade;
    }

    public StoreMenuDto(StoreMenu storeMenu) {
        this.id = storeMenu.getId();
        this.menuName = storeMenu.getMenuName();
        this.menuPrice = storeMenu.getMenuPrice();
        this.vegetarianGrade = storeMenu.getVegetarianGrade();
    }
}

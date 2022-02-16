package zerogreen.eco.dto.store;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import zerogreen.eco.entity.userentity.VegetarianGrade;

@Setter
@Getter
@Slf4j
public class StoreMenuDto {

    private Long id;
    private String menuName;
    private int menuPrice;
    private VegetarianGrade vegetarianGrade;


    public StoreMenuDto(){}

    public StoreMenuDto(Long id, String menuName, int menuPrice, VegetarianGrade vegetarianGrade){
        this.id = id;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.vegetarianGrade = vegetarianGrade;
    }

    public StoreMenuDto(String menuName, int menuPrice, VegetarianGrade vegetarianGrade){
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.vegetarianGrade = vegetarianGrade;
    }
}

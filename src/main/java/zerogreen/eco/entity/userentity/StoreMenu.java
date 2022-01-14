package zerogreen.eco.entity.userentity;

import lombok.Getter;
import lombok.ToString;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString(exclude = {"foodStore", "ecoStore"})
public class StoreMenu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    private String menuName;
    private int menuPrice;
    private VegetarianGrade vegetarianGrades;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "food_id")
    private FoodStoreUser foodStore;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "eco_id")
    private EcoStoreUser ecoStore;

    public StoreMenu(String menuName, int menuPrice, VegetarianGrade vegetarianGrades, FoodStoreUser foodStore, EcoStoreUser ecoStore) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.vegetarianGrades = vegetarianGrades;
        this.foodStore = foodStore;
        this.ecoStore = ecoStore;
    }
}

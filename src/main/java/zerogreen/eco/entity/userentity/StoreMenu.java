package zerogreen.eco.entity.userentity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString(exclude = {"storeMember"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreMenu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    private String menuName;
    private int menuPrice;

    @Enumerated(EnumType.STRING)
    private VegetarianGrade vegetarianGrade;

    @ManyToOne(fetch = LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private StoreMember storeMember;

    public StoreMenu(String menuName, int menuPrice, VegetarianGrade vegetarianGrade, StoreMember storeMember) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.vegetarianGrade = vegetarianGrade;
        this.storeMember = storeMember;
    }

    /*
    * Setter
    * */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public void setVegetarianGrade(VegetarianGrade vegetarianGrade) {
        this.vegetarianGrade = vegetarianGrade;
    }

    public void setStoreMember(StoreMember storeMember) {
        this.storeMember = storeMember;
    }
}

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
    private VegetarianGrade vegetarianGrades;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private StoreMember storeMember;

    public StoreMenu(String menuName, int menuPrice, VegetarianGrade vegetarianGrades, StoreMember storeMember) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.vegetarianGrades = vegetarianGrades;
        this.storeMember = storeMember;
    }
}

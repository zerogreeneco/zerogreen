package zerogreen.eco;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.userentity.*;
import zerogreen.eco.service.user.BasicUserService;
import zerogreen.eco.service.user.MemberService;
import zerogreen.eco.service.user.StoreMemberService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final InitService initService;

    @PostConstruct
    public void initService() {
        initService.init();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        private final MemberService memberService;
        private final StoreMemberService storeMemberService;
        private final BasicUserService basicUserService;

        public void init() {
            StoreMember ecoTest = new StoreMember("ecoTest", "01033334444", "1",
                    UserRole.UN_STORE, "ECO_SHOP", "REGNUM1111",StoreType.ECO_SHOP,
                    "부산시 해운대구", "0517778888",  "11111");

            StoreMember foodTest = new StoreMember("foodTest", "01044445555", "1",
                    UserRole.UN_STORE, "VEGAN_FOOD", "0987654321", StoreType.VEGAN_FOOD,
                    "부산시 동래구", "0519998888", "99999");

            StoreMember noVegan = new StoreMember("noVeganlTest", "01044445555", "1",
                    UserRole.UN_STORE, "NO_VEGAN_FOOD", "785654321", StoreType.GENERAL_FOOD,
                    "부산시 연제구", "0517776666", "88888");

            // 일반 회원
            memberService.save(new Member("test", "tester", "01022223333", "1",
                    UserRole.USER, VegetarianGrade.LACTO));
            // 가게 회원
            storeMemberService.save(ecoTest, new RegisterFile("origin1","store1", "path1"));
            storeMemberService.save(foodTest, new RegisterFile("origin2","store2", "path2"));
            storeMemberService.save(noVegan, new RegisterFile("origin","store3", "path3"));


            // 관리자 계정
            basicUserService.adminSave();

            em.flush();
            em.clear();

        }


    }

}

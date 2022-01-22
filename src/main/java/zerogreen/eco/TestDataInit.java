package zerogreen.eco;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.userentity.*;
import zerogreen.eco.repository.user.StoreMemberRepository;
import zerogreen.eco.service.user.BasicUserService;
import zerogreen.eco.service.user.MemberService;
import zerogreen.eco.service.user.StoreMemberService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

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

        private final StoreMemberRepository storeMemberRepository;

        public void init() {
            StoreMember ecoTest = new StoreMember("ecoTest", "01033334444", "1", UserRole.STORE, "1234567890", StoreType.ECO_SHOP);
            StoreMember foodTest = new StoreMember("foodTest", "01044445555", "1", UserRole.STORE, "0987654321", StoreType.FOOD);

            ecoTest.setStoreInfo(new StoreInfo("부산시 해운대구", "0519998888",null,
                    LocalDateTime.now(), LocalDateTime.now()));

            memberService.save(new Member("test", "tester", "01022223333", "1", UserRole.USER, VegetarianGrade.LACTO));
            storeMemberService.save(ecoTest, new RegisterFile("origin","store", "path"));
            storeMemberService.save(foodTest, new RegisterFile("origin","store", "path"));
            basicUserService.adminSave();

            em.flush();
            em.clear();

            StoreMember updateEco = storeMemberRepository.findById(2L).get();
            updateEco.setStoreInfo(new StoreInfo( "부산시 해운대구", "0517778888",null,
                    LocalDateTime.now(), LocalDateTime.now()));

            storeMemberService.storeInfoSave(updateEco);

            StoreMember updateFood = storeMemberRepository.findById(3L).get();
            updateFood.setStoreInfo(new StoreInfo( "부산시 동래구", "0515556666",null,
                    LocalDateTime.now(), LocalDateTime.now()));

            storeMemberService.storeInfoSave(updateFood);

        }


    }

}

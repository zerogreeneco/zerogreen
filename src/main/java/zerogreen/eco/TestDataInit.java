package zerogreen.eco;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.entity.userentity.*;
import zerogreen.eco.repository.user.EcoStoreRepository;
import zerogreen.eco.repository.user.FoodStoreRepository;
import zerogreen.eco.service.user.EcoStoreService;
import zerogreen.eco.service.user.FoodStoreService;
import zerogreen.eco.service.user.MemberService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

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
        private final EcoStoreService ecoStoreService;
        private final FoodStoreService foodStoreService;

        private final EcoStoreRepository ecoStoreRepository;
        private final FoodStoreRepository foodStoreRepository;

        public void init() {
            EcoStoreUser ecoTest = new EcoStoreUser("ecoTest", null, "01033334444", "1", UserRole.STORE, "1234567890");
            FoodStoreUser foodTest = new FoodStoreUser("foodTest", null, "01044445555", "1", UserRole.STORE, "0987654321");

            ecoTest.setStoreInfo(new StoreInfo("ECO STORE","부산시 해운대구", "0519998888",
                    LocalDateTime.now(), LocalDateTime.now()));

            memberService.save(new Member("test", "tester", "01022223333", "1", UserRole.USER, VegetarianGrade.LACTO));
            ecoStoreService.save(ecoTest);
            foodStoreService.save(foodTest);

            em.flush();
            em.clear();

            EcoStoreUser updateEco = ecoStoreRepository.findById(2L).get();
            updateEco.setStoreInfo(new StoreInfo("ECO STORE", "부산시 해운대구", "0517778888",
                    LocalDateTime.now(), LocalDateTime.now()));

            ecoStoreService.storeInfoSave(updateEco);

            FoodStoreUser updateFood = foodStoreRepository.findById(3L).get();
            updateFood.setStoreInfo(new StoreInfo("FOOD STORE", "부산시 동래구", "0515556666",
                    LocalDateTime.now(), LocalDateTime.now()));

            foodStoreService.storeInfoSave(updateFood);

        }


    }

}

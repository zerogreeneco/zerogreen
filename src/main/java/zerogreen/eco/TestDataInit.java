package zerogreen.eco;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.community.CommunityRequestDto;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.userentity.*;
import zerogreen.eco.service.community.CommunityBoardService;
import zerogreen.eco.service.user.BasicUserService;
import zerogreen.eco.service.user.MemberService;
import zerogreen.eco.service.user.StoreMemberService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

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
    @Slf4j
    static class InitService {
        private final EntityManager em;

        private final MemberService memberService;
        private final StoreMemberService storeMemberService;
        private final BasicUserService basicUserService;
        private final CommunityBoardService communityBoardService;

        public void init() {

            List<String> ecoAddress = new ArrayList<>();
            ecoAddress.add("부산광역시 수영구 장대골로 76");
            ecoAddress.add("부산광역시 북구 덕천1길 93 2층");
            ecoAddress.add("부산광역시 부산진구 성지로14번길 13");
            ecoAddress.add("부산광역시 해운대구 대천로103번길 61");
            ecoAddress.add("부산광역시 연제구 신금로 25 2층 227호");
            ecoAddress.add("부산광역시 수영구 민락로33번길 5");
            ecoAddress.add("부산광역시 부산진구 중앙대로 821");
            ecoAddress.add("부산광역시 기장군 정관읍 모전3길 32-8 1층");
            ecoAddress.add("부산광역시 남구 수영로 149 2층");
            ecoAddress.add("부산광역시 사하구 낙동대로 129 지하1F");

            List<String> foodAddress = new ArrayList<>();
            foodAddress.add("부산광역시 부산진구 동성로87번길 9 2층");
            foodAddress.add("부산광역시 남구 수영로39번나길 53 미인당");
            foodAddress.add("부산광역시 동래구 온천천로471번가길 20");
            foodAddress.add("부산광역시 서구 구덕로124번길 13 3층");
            foodAddress.add("부산광역시 해운대구 해운대해변로 280");
            foodAddress.add("부산광역시 동구 중앙대로 526");
            foodAddress.add("부산광역시 해운대구 센텀중앙로 145 2상가 104호");
            foodAddress.add("부산광역시 수영구 망미번영로70번길 16 1층");
            foodAddress.add("부산광역시 동래구 온천천로471번길 7 1층 104호");
            foodAddress.add("부산광역시 부산진구 전포대로186번길 33 1층 그리구요거트");
            foodAddress.add("부산광역시 수영구 광안해변로 193");
            foodAddress.add("부산광역시 수영구 수영로510번길 42");
            foodAddress.add("부산광역시 북구 금곡대로303번길 36 스페이스303 1층 106호");
            foodAddress.add("부산광역시 남구 진남로 54 대동래미안3 수수베이커리");
            foodAddress.add("부산광역시 부산진구 범양로120번길 6 1층,세자매바른빵");
            foodAddress.add("부산광역시 금정구 중앙대로 1667 삼신빌딩 1층");
            foodAddress.add("부산광역시 동래구 동래로79번길 26 1층");

            List<String> general = new ArrayList<>();
            general.add("부산광역시 중구 중앙대로 17 광복지하도상가A57~59");
            general.add("부산광역시 연제구 아시아드대로19번길 16");
            general.add("부산광역시 강서구 명지국제5로 165");
            general.add("부산광역시 해운대구 선수촌로 95 센텀대림아파트 대림상가 12동 3층");
            general.add("부산 수영구 민락수변로 29 3층 디에이블");
            general.add("부산 부산진구 동천로 92 NC서면점 5층");
            general.add("부산 동구 중앙대로209번길 12");
            general.add("부산 부산진구 서면문화로 19");
            general.add("부산 부산진구 서면로 26");

            for (int i = 0; i < ecoAddress.size(); i++) {
                storeMemberService.saveV2(new StoreMember("ecoTest"+i, "01033334444", "1",
                        UserRole.UN_STORE, "ECO_SHOP"+i, "REGNUM1111",StoreType.ECO_SHOP,
                        ecoAddress.get(i), "","0517778888",  "11111"),new RegisterFile("origin3","store3", "path3"));
            }

            for (int i = 0; i < foodAddress.size(); i++) {
                storeMemberService.saveV2(new StoreMember("foodTest"+i, "01033334444", "1",
                        UserRole.UN_STORE, "FOOD_SHOP"+i, "REGNUM1111",StoreType.VEGAN_FOOD,
                        foodAddress.get(i), "","0517778888",  "11111"),new RegisterFile("origin3","store3", "path3"));
            }

            for (int i = 0; i < general.size(); i++) {
                storeMemberService.saveV2(new StoreMember("generalTest"+i, "01033334444", "1",
                        UserRole.UN_STORE, "GENERAL_SHOP"+i, "REGNUM1111",StoreType.GENERAL_FOOD,
                        general.get(i), "","0517778888",  "11111"),new RegisterFile("origin3","store3", "path3"));
            }

            StoreMember ecoTest = new StoreMember("ecoTest", "01033334444", "1",
                    UserRole.UN_STORE, "ECO_SHOP", "REGNUM1111",StoreType.ECO_SHOP,
                    "부산 해운대구 중동2로 11", "우동","0517778888",  "11111");

            StoreMember foodTest = new StoreMember("foodTest", "01044445555", "1",
                    UserRole.UN_STORE, "VEGAN_FOOD", "0987654321", StoreType.VEGAN_FOOD,
                    "부산 동래구 온천천로359번길 70", "안락동","0519998888", "99999");

            StoreMember noVegan = new StoreMember("noVeganlTest", "01044445555", "1",
                    UserRole.UN_STORE, "NO_VEGAN_FOOD", "785654321", StoreType.GENERAL_FOOD,
                    "부산 연제구 연제로 2", "연산동","0517776666", "88888");

            StoreMember ecoTest2 = new StoreMember("ecoTest2", "01012345678", "1",
                    UserRole.STORE, "ECO_SHOP2", "0511234567",StoreType.ECO_SHOP,
                    "부산 사하구 낙동대로398번길 12", "하단동","0511234567",  "22222");

            StoreMember foodTest2 = new StoreMember("foodTest2", "01098765432", "1",
                    UserRole.STORE, "VEGAN_FOOD2", "0519876543", StoreType.VEGAN_FOOD,
                    "부산시 동래구 명장로 22번길 29", "덕천동","0519876541", "33333");

            StoreMember noVegan2 = new StoreMember("noVeganlTest2", "01078945612", "1",
                    UserRole.STORE, "NO_VEGAN_FOOD2", "0517894561", StoreType.GENERAL_FOOD,
                    "부산 금정구 중앙대로 1777", "장전동","0517894561", "44444");

            // 일반 회원
            Member member = new Member("test", "tester", "01022223333", "1",
                    UserRole.USER, VegetarianGrade.LACTO);
            CommunityRequestDto dto = new CommunityRequestDto("TEST TITLE", "TEST TEXT", Category.PLOGGING);
            memberService.save(member);

            Member member2 = new Member("test2", "tester2", "01022224444", "1",
                    UserRole.USER, VegetarianGrade.LACTO);
            CommunityRequestDto dto2 = new CommunityRequestDto("TEST TITLE2", "TEST TEXT2", Category.PLOGGING);
            memberService.save(member2);

            // 가게 회원
            storeMemberService.save(ecoTest, new RegisterFile("origin1","store1", "path1"));
            storeMemberService.save(foodTest, new RegisterFile("origin2","store2", "path2"));
            storeMemberService.save(noVegan, new RegisterFile("origin3","store3", "path3"));
            storeMemberService.saveV2(ecoTest2, new RegisterFile("origin1","store1", "path1"));
            storeMemberService.saveV2(foodTest2, new RegisterFile("origin2","store2", "path2"));
            storeMemberService.saveV2(noVegan2, new RegisterFile("origin3","store3", "path3"));

            // 관리자 계정
            basicUserService.adminSave();

            em.flush();
            em.clear();
        }


    }

}

package zerogreen.eco.service.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.store.StoreMenuDto;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.StoreMenu;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.repository.store.StoreMenuRepository;
import zerogreen.eco.repository.user.StoreMemberRepository;

import javax.mail.Store;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreMenuServiceImpl implements StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;
    private final StoreMemberRepository storeMemberRepository;

    @Transactional
    @Override
    public void updateStoreMenu(Long id, String menuName, int menuPrice, VegetarianGrade vegetarianGrade) {
        StoreMember storeMember = storeMemberRepository.findById(id).orElseThrow();
        log.info("KKKKK"+id);
        log.info("KKKKK"+menuName);
        log.info("KKKKK"+menuPrice);
//        log.info("KKKKK"+vegetarianGrade);
        storeMenuRepository.save(new StoreMenu(menuName, menuPrice, vegetarianGrade ,storeMember));
    }
}

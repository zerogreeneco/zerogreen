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

import java.util.List;
import java.util.stream.Collectors;

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

        storeMenuRepository.save(new StoreMenu(menuName, menuPrice, vegetarianGrade ,storeMember));
    }

    @Override
    public List<StoreMenuDto> getStoreMenu(Long id) {
        List<StoreMenu> tableList = storeMenuRepository.getStoreMenu(id);
        return tableList.stream().map(StoreMenuDto::new).collect(Collectors.toList());
    }

    @Override
    public void menuDelete(Long id) {
        storeMenuRepository.delete(id);
    }
}

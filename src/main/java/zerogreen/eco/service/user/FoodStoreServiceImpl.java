package zerogreen.eco.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.entity.userentity.EcoStoreUser;
import zerogreen.eco.entity.userentity.FoodStoreUser;
import zerogreen.eco.entity.userentity.StoreInfo;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.user.FoodStoreRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodStoreServiceImpl implements FoodStoreService{

    private final FoodStoreRepository foodStoreRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Long save(FoodStoreUser foodStore) {
        String encPassword = passwordEncoder.encode(foodStore.getPassword());

        return foodStoreRepository.save(new FoodStoreUser(foodStore.getUsername(),
                null, foodStore.getPhoneNumber(), encPassword, UserRole.STORE, foodStore.getStoreRegNum())).getId();
    }

    @Transactional
    @Override
    public void storeInfoSave(FoodStoreUser foodStore) {
        FoodStoreUser findMember = foodStoreRepository.findById(foodStore.getId()).orElseGet(null);

        StoreInfo storeInfo = findMember.getStoreInfo();
        findMember.setStoreInfo(findMember.getStoreInfo());

    }
}

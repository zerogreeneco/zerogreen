package zerogreen.eco.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.entity.userentity.EcoStoreUser;
import zerogreen.eco.entity.userentity.StoreInfo;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.user.EcoStoreRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EcoStoreServiceImpl implements EcoStoreService{

    private final EcoStoreRepository ecoStoreRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Long save(EcoStoreUser ecoStore) {

        String encPassword = passwordEncoder.encode(ecoStore.getPassword());

        return ecoStoreRepository.save(new EcoStoreUser(ecoStore.getUsername(), null, ecoStore.getPhoneNumber(),
                encPassword, UserRole.STORE, ecoStore.getStoreRegNum())).getId();
    }

    @Transactional
    @Override
    public void storeInfoSave(EcoStoreUser ecoStore) {
        EcoStoreUser findMember = ecoStoreRepository.findById(ecoStore.getId()).orElseGet(null);

        StoreInfo storeInfo = findMember.getStoreInfo();
        findMember.setStoreInfo(findMember.getStoreInfo());

    }
}

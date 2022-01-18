package zerogreen.eco.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.entity.userentity.StoreInfo;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.user.StoreMemberRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreMemberServiceImpl implements StoreMemberService{

    private final StoreMemberRepository storeMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Long save(StoreMember storeMember) {

        String encPassword = passwordEncoder.encode(storeMember.getPassword());

        return storeMemberRepository.save(new StoreMember(storeMember.getUsername(), null, storeMember.getPhoneNumber(),
                encPassword, UserRole.STORE, storeMember.getStoreRegNum(), storeMember.getStoreType())).getId();
    }

    @Transactional
    @Override
    public void storeInfoSave(StoreMember storeMember) {
        StoreMember findMember = storeMemberRepository.findById(storeMember.getId()).orElseGet(null);

        StoreInfo storeInfo = findMember.getStoreInfo();
        findMember.setStoreInfo(findMember.getStoreInfo());

    }
}

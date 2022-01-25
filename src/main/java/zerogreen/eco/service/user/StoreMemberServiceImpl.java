package zerogreen.eco.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.userentity.StoreInfo;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.user.StoreMemberRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreMemberServiceImpl implements StoreMemberService {

    private final StoreMemberRepository storeMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Long save(StoreMember storeMember, RegisterFile registerFile) {

        String encPassword = passwordEncoder.encode(storeMember.getPassword());
        return storeMemberRepository.save(new StoreMember(storeMember.getUsername(), storeMember.getPhoneNumber(),
                        encPassword, UserRole.UN_STORE, storeMember.getStoreName(), storeMember.getStoreRegNum(), storeMember.getStoreType(),
                        storeMember.getStoreInfo().getStoreAddress(), storeMember.getStoreInfo().getStorePhoneNumber(), registerFile, storeMember.getStoreInfo().getPostalCode()))
                .getId();
    }

    @Transactional
    @Override
    public void storeInfoSave(StoreMember storeMember) {
        StoreMember findMember = storeMemberRepository.findById(storeMember.getId()).orElseGet(null);

        StoreInfo storeInfo = findMember.getStoreInfo();
        findMember.setStoreInfo(findMember.getStoreInfo());

    }

    @Transactional
    @Override
    public void getStore(Long id) {
        StoreMember getStoreMember = storeMemberRepository.getById(id);
    }

    //승인받은 사업자회원들
    @Override
    public List<StoreDto> findByApprovedStore() {
        return storeMemberRepository.findByApprovedStore();
    }

    //임시 리스트
    @Override
    public List<StoreMember> findAll() {
        return storeMemberRepository.findAll();
    }

    @Override
    public StoreDto getStoreTemp(Long id) {
        StoreMember storeMember = storeMemberRepository.getById(id);
        return entityToDto(storeMember);
    }


}
package zerogreen.eco.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.entity.userentity.StoreInfo;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.entity.userentity.*;
import zerogreen.eco.repository.file.StoreImageFileRepository;
import zerogreen.eco.repository.store.StoreMenuRepository;
import zerogreen.eco.repository.user.StoreMemberRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreMemberServiceImpl implements StoreMemberService {

    private final StoreMemberRepository storeMemberRepository;
    private final StoreImageFileRepository storeImageFileRepository;
    private final StoreMenuRepository storeMenuRepository;
    @Lazy
    private final PasswordEncoder passwordEncoder;

    /*
     * Store_member 회원가입(RegisterFile 포함)
     * */
    @Transactional
    @Override
    public Long save(StoreMember storeMember, RegisterFile registerFile) {

        String encPassword = passwordEncoder.encode(storeMember.getPassword());

        return storeMemberRepository.save(StoreMember.builder()
                        .username(storeMember.getUsername())
                        .password(encPassword)
                        .phoneNumber(storeMember.getPhoneNumber())
                        .userRole(UserRole.UN_STORE)
                        .storeName(storeMember.getStoreName())
                        .storeRegNum(storeMember.getStoreRegNum())
                        .storeType(storeMember.getStoreType())
                        .storeAddress(storeMember.getStoreInfo().getStoreAddress())
                        .storeDetailAddress(storeMember.getStoreInfo().getStoreDetailAddress())
                        .postalCode(storeMember.getStoreInfo().getPostalCode())
                        .storePhoneNumber(storeMember.getStoreInfo().getStorePhoneNumber())
                        .registerFile(registerFile)
                        .build())
                .getId();
    }

    //임시
    @Transactional
    @Override
    public Long saveV2(StoreMember storeMember, RegisterFile registerFile) {

        String encPassword = passwordEncoder.encode(storeMember.getPassword());
        return storeMemberRepository.save(new StoreMember(storeMember.getUsername(), storeMember.getPhoneNumber(),
                        encPassword, UserRole.STORE, storeMember.getStoreName(), storeMember.getStoreRegNum(), storeMember.getStoreType(),
                        storeMember.getStoreInfo().getStoreAddress(), storeMember.getStoreInfo().getStoreDetailAddress(),storeMember.getStoreInfo().getStorePhoneNumber(), registerFile, storeMember.getStoreInfo().getPostalCode()))
                .getId();
    }

    @Transactional
    @Override
    public void storeInfoSave(StoreMember storeMember) {
        StoreMember findMember = storeMemberRepository.findById(storeMember.getId()).orElseGet(null);

        StoreInfo storeInfo = findMember.getStoreInfo();
        findMember.setStoreInfo(findMember.getStoreInfo());
    }


    //상세페이지 ** 아래 주석포함 작업중**
    @Override
    public StoreDto getStore(Long sno) {
        return storeMemberRepository.getStoreById(sno);
    }

/*
    public StoreDto getStore(Long sno) {
        StoreMember storeMember = storeMemberRepository.getStoreById(sno);
        log.info("??????????"+storeMember);
        return StoreDto.builder()
                .storeMember(storeMember)
                .imageFiles(storeMember.getImageFiles())
                .socialAddresses(storeMember.getSocialAddresses())
                .menuList(storeMember.getMenuList())
                .build();
    }
*/

    /*
    * 이미지 DB 저장
    * */
    @Transactional
    @Override
    public void imageSave(Long id, List<StoreImageFile> storeImageFile) {
        log.info("IDID={}", id);
        StoreMember findMember = storeMemberRepository.findById(id).orElseThrow();
        log.info("findMember={}", findMember.getUsername());

        for (StoreImageFile image : storeImageFile) {
            storeImageFileRepository.save(new StoreImageFile(image.getFileName(),
                    image.getStoreFileName(), image.getFilePath(), findMember));
        }

        log.info("파일 저장 OK");
    }

    @Override
    public List<NonApprovalStoreDto> findByApprovalStore(UserRole userRole) {
        return storeMemberRepository.findByApprovalStore(userRole);
    }

    @Override
    @Transactional
    public Slice<StoreDto> getShopList(Pageable pageable) {
       return storeMemberRepository.getShopList(pageable);
    }
}
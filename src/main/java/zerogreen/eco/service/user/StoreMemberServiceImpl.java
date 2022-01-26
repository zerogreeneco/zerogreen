package zerogreen.eco.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.entity.userentity.StoreInfo;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.file.StoreImageFileRepository;
import zerogreen.eco.repository.user.StoreMemberRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreMemberServiceImpl implements StoreMemberService {

    private final StoreMemberRepository storeMemberRepository;
    private final StoreImageFileRepository storeImageFileRepository;
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


    //임시 리스트
    @Override
    public List<StoreMember> findAll() {
        return storeMemberRepository.findAll();
    }

    //store데이터 넘겨서 상세페이지에.. 근데 수정될 가능성 99%
    @Override
    public StoreDto getStore(Long id) {
        StoreMember storeMember = storeMemberRepository.getById(id);
        return new StoreDto(storeMember.getStoreName(), storeMember.getStoreRegNum(), storeMember.getStoreType(),
                storeMember.getId(), storeMember.getUsername(), storeMember.getUserRole(), storeMember.getImageFiles(),
                storeMember.getStoreInfo().getPostalCode(), storeMember.getStoreInfo().getStoreAddress(),
                storeMember.getStoreInfo().getStorePhoneNumber(), storeMember.getStoreInfo().getOpenTime(), storeMember.getStoreInfo().getCloseTime(),
                storeMember.getMenuList());
    }

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
}
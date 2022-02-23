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
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.detail.LikesRepository;
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
    private final LikesRepository likesRepository;
    @Lazy
    private final PasswordEncoder passwordEncoder;

    /*
     * Store_member 회원가입(RegisterFile 포함)
     * */
    @Transactional
    @Override
    public Long save(StoreMember storeMember, RegisterFile registerFile) {

        String encPassword = passwordEncoder.encode(storeMember.getPassword());

        return storeMemberRepository.save(StoreMember.regBuilder()
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

    //test data
    @Transactional
    @Override
    public Long saveV2(StoreMember storeMember, RegisterFile registerFile) {
        String encPassword = passwordEncoder.encode(storeMember.getPassword());
        return storeMemberRepository.save(new StoreMember(storeMember.getUsername(), storeMember.getPhoneNumber(),
                        encPassword, UserRole.STORE, storeMember.getStoreName(), storeMember.getStoreRegNum(), storeMember.getStoreType(), storeMember.getStoreInfo().getPostalCode(),
                        storeMember.getStoreInfo().getStoreAddress(), storeMember.getStoreInfo().getStoreDetailAddress(), storeMember.getStoreInfo().getStorePhoneNumber(), registerFile))
                .getId();
    }

    //test data V2,
    @Transactional
    @Override
    public Long saveV3(StoreMember storeMember) {
        String encPassword = passwordEncoder.encode(storeMember.getPassword());
        RegisterFile registerFile1 = new RegisterFile("testFile", "testFile", "testFile");
        return storeMemberRepository.save(new StoreMember(storeMember.getUsername(), storeMember.getPhoneNumber(),
                        encPassword, UserRole.STORE, storeMember.getStoreName(), storeMember.getStoreRegNum(), storeMember.getStoreType(), storeMember.getStoreInfo().getPostalCode(),
                        storeMember.getStoreInfo().getStoreAddress(), storeMember.getStoreInfo().getStoreDetailAddress(),storeMember.getStoreInfo().getStorePhoneNumber(),
                        storeMember.getStoreInfo().getStoreDescription(), storeMember.getStoreInfo().getSocialAddress1() ,storeMember.getStoreInfo(). getSocialAddress2(),
                        storeMember.getStoreInfo().getOpenTime(), storeMember.getStoreInfo().getCloseTime(), registerFile1))
                .getId();
    }


    @Transactional
    @Override
    public void storeInfoSave(StoreMember storeMember) {
        StoreMember findMember = storeMemberRepository.findById(storeMember.getId()).orElseGet(null);
        StoreInfo storeInfo = findMember.getStoreInfo();
        findMember.setStoreInfo(findMember.getStoreInfo());
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
                    image.getStoreFileName(), image.getThumbnailName(), image.getFilePath(), findMember));
        }

        log.info("파일 저장 OK");
    }


    //상세페이지
    @Override
    public StoreDto getStore(Long sno) {
        StoreMember storeMember = storeMemberRepository.getById(sno);
        return StoreDto.builder()
                .sno(storeMember.getId())
                .storeName(storeMember.getStoreName())
                .storeType(storeMember.getStoreType())
                .storeInfo(storeMember.getStoreInfo())
                .count(likesRepository.counting(storeMember.getId()))
                .build();
    }


    @Override
    public List<NonApprovalStoreDto> findByApprovalStore(UserRole userRole) {
        return storeMemberRepository.findByApprovalStore(userRole);
    }

    //Shop List 출력
    @Override
    @Transactional
    public Slice<StoreDto> getShopList(Pageable pageable) {
        return storeMemberRepository.getShopList(pageable);
    }

    //Food List 출력
    @Override
    @Transactional
    public Slice<StoreDto> getFoodList(Pageable pageable) {
        return storeMemberRepository.getFoodList(pageable);
    }

    @Override
    public Slice<StoreDto> getFoodTypeList(Pageable pageable, StoreType storeType) {
        return storeMemberRepository.getFoodTypeList(pageable,storeType);
    }

    @Override
    public StoreDto storeInfo(Long id, StoreDto storeDto) {
        StoreMember storeMember = storeMemberRepository.findById(id).orElseThrow();
        StoreDto info = new StoreDto(storeMember.getStoreName(), storeMember.getStoreType(),
                storeMember.getStoreInfo().getStorePhoneNumber(), storeMember.getStoreInfo().getOpenTime(),
                storeMember.getStoreInfo().getCloseTime(), storeMember.getStoreInfo().getStoreDescription(),
                storeMember.getStoreInfo().getSocialAddress1(), storeMember.getStoreInfo().getSocialAddress2());

        return info;
    }

    @Transactional
    @Override
    public void updateStore(Long id, StoreDto storeDto) {
        StoreMember storeMember = storeMemberRepository.findById(id).orElseThrow();
        //더티 체킹
        storeMember.getStoreInfo().setStorePhoneNumber(storeDto.getStorePhoneNumber());
        storeMember.getStoreInfo().setOpenTime(storeDto.getOpenTime());
        storeMember.getStoreInfo().setCloseTime(storeDto.getCloseTime());
        storeMember.getStoreInfo().setStoreDescription(storeDto.getStoreDescription());
        storeMember.getStoreInfo().setSocialAddress1(storeDto.getSocialAddress1());
        storeMember.getStoreInfo().setSocialAddress2(storeDto.getSocialAddress2());
    }

    @Override
    public int countByStoreRegNum(String storeRegNum) {
        return storeMemberRepository.countByStoreRegNum(storeRegNum);
    }

}
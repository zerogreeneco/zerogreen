package zerogreen.eco.service.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;

import java.util.List;

public interface StoreMemberService {
    //스토어 회원가입
    Long save(StoreMember storeMember, RegisterFile registerFile);
    //스토어 회원가입 테스트 데이터용
    Long saveV2(StoreMember storeMember, RegisterFile register);
    void storeInfoSave(StoreMember storeMember);

    void imageSave(Long id, List<StoreImageFile> storeImageFile);

    List<NonApprovalStoreDto> findByApprovalStore(UserRole userRole);
    //스토어 db 불러오기
    StoreDto getStore(Long sno);

    Slice<StoreDto> getShopList(Pageable pageable);

    }

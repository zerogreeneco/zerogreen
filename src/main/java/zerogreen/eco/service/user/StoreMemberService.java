package zerogreen.eco.service.user;

import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.entity.userentity.StoreMember;

import java.util.List;

public interface StoreMemberService {
    Long save(StoreMember storeMember, RegisterFile registerFile);
    Long saveV2(StoreMember storeMember, RegisterFile register);
    void storeInfoSave(StoreMember storeMember);

    void imageSave(Long id, List<StoreImageFile> storeImageFile);

    //임시리스트
    //List<StoreMember> findAll();
    StoreDto getStore(Long id);

    }

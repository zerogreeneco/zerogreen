package zerogreen.eco.service.user;

import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.userentity.StoreMember;

import java.util.List;

public interface StoreMemberService {
    public Long save(StoreMember storeMember, RegisterFile registerFile);
    public Long saveV2(StoreMember storeMember, RegisterFile registerFile);

    public void storeInfoSave(StoreMember storeMember);


    //임시리스트
    List<StoreMember> findAll();
    StoreDto getStore(Long id);

    }

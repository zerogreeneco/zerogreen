package zerogreen.eco.service.user;

import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.userentity.StoreMember;

import java.util.List;

public interface StoreMemberService {
    public Long save(StoreMember storeMember, RegisterFile registerFile);

    public void storeInfoSave(StoreMember storeMember);

    public void getStore(Long id);

    public List<StoreDto> findByApprovedStore();

    //임시리스트
    List<StoreMember> findAll();
    StoreDto getStoreTemp(Long id);

    //일단 임시로... ^.ㅠ
    default StoreDto entityToDto (StoreMember storeMember) {
        StoreDto storeDto = StoreDto.builder()
                .storeName(storeMember.getStoreName())
                .storeRegNum(storeMember.getStoreRegNum())
                .storeType(storeMember.getStoreType())
                .storeInfo(storeMember.getStoreInfo())
                .userRole(storeMember.getUserRole())
                .id(storeMember.getId())
                .username(storeMember.getUsername())
                .build();
        return storeDto;
    }


    }

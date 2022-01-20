package zerogreen.eco.service.user;

import zerogreen.eco.entity.userentity.StoreMember;

public interface StoreMemberService {
    public Long save(StoreMember storeMember);

    public void storeInfoSave(StoreMember storeMember);

    public void getStore(Long id);

    }

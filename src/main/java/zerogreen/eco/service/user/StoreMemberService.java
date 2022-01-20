package zerogreen.eco.service.user;

import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.userentity.StoreMember;

public interface StoreMemberService {
    public Long save(StoreMember storeMember, RegisterFile registerFile);

    public void storeInfoSave(StoreMember storeMember);
}

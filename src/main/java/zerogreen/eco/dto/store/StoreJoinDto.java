package zerogreen.eco.dto.store;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.dto.member.MemberJoinDto;
import zerogreen.eco.entity.userentity.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StoreJoinDto {
    @NotBlank
    @Email
    private String username;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;

    @NotBlank
    private String storeRegNum;

    private StoreType storeType;

    private MultipartFile attachFile;

    public StoreMember toStoreMember(StoreJoinDto storeDto) {
        return new StoreMember(storeDto.getUsername(),storeDto.getPhoneNumber(),storeDto.getPassword(), UserRole.UNSTORE,
                storeDto.getStoreRegNum(), storeDto.getStoreType());
    }
}

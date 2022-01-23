package zerogreen.eco.dto.store;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.entity.userentity.UserRole;

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
    private String storeName;

    @NotBlank
    private String password;

    @NotBlank
    private String storeRegNum;

    private StoreType storeType;

    private MultipartFile attachFile;

    public StoreJoinDto() {
    }

    public StoreMember toStoreMember(StoreJoinDto storeDto) {

        return new StoreMember(storeDto.getUsername(), storeDto.getPhoneNumber(), storeDto.getPassword(), UserRole.UNSTORE,
                storeDto.getStoreName(), storeDto.getStoreRegNum(), storeDto.getStoreType());
    }
}

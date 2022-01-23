package zerogreen.eco.dto.store;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.entity.userentity.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Slf4j
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

    @NotBlank
    private String storePhoneNumber;

    @NotBlank
    private String storeAddress;

    @NotNull
    private int postalCode;

    private StoreType storeType;

    private MultipartFile attachFile;

    public StoreJoinDto() {
    }

    public StoreMember toStoreMember(StoreJoinDto storeDto) {
        log.info("DTODTO={}",storeDto.getStoreAddress());
        log.info("DTODTO={}",storeDto.getPostalCode());
        log.info("DTODTO={}",storeDto.getStorePhoneNumber());
        return new StoreMember(storeDto.getUsername(), storeDto.getPhoneNumber(), storeDto.getPassword(),
                UserRole.UNSTORE, storeDto.getStoreName(), storeDto.getStoreRegNum(),
                storeDto.getStoreType(), storeDto.getStoreAddress(), storeDto.getStorePhoneNumber(), storeDto.getPostalCode());
    }
}

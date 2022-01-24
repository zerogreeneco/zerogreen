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
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Slf4j
public class StoreJoinDto {
    @NotBlank
    @Email
    private String username;

    @NotBlank(message = "공백 X")
    private String phoneNumber;

    @NotBlank(message = "가게이름")
    private String storeName;

    @NotBlank
//    @Pattern(regexp="[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String password;

    @NotBlank
    private String storeRegNum;

    @NotBlank
    private String storePhoneNumber;

    @NotBlank
    private String storeAddress;

    @NotNull
    private String postalCode;

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

package zerogreen.eco.dto.store;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NonApprovalStoreDto {

    private Long fileId;
    private Long memberId;
    private String username;
    private String storeName;
    private String storePhoneNumber;
    private String storeAddress;
    private String storeRegNum;
    private String uploadFileName;

    public NonApprovalStoreDto() {
    }

    public NonApprovalStoreDto(Long memberId, String username, String storeName, String storePhoneNumber,
                               String storeAddress, String storeRegNum, Long fileId ,String uploadFileName) {
        this.fileId = fileId;
        this.memberId = memberId;
        this.username = username;
        this.storeName = storeName;
        this.storePhoneNumber = storePhoneNumber;
        this.storeAddress = storeAddress;
        this.storeRegNum = storeRegNum;
        this.uploadFileName = uploadFileName;
    }
}

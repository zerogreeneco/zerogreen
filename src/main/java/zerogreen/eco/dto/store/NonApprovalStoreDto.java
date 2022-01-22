package zerogreen.eco.dto.store;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NonApprovalStoreDto {

    private String username;
    private String storeRegNum;
    private Long id;
    private String uploadFileName;

    public NonApprovalStoreDto() {
    }

    public NonApprovalStoreDto(String username, String storeRegNum, Long id, String uploadFileName) {
        this.username = username;
        this.storeRegNum = storeRegNum;
        this.id = id;
        this.uploadFileName = uploadFileName;
    }
}

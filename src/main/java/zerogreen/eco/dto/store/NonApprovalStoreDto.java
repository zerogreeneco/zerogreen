package zerogreen.eco.dto.store;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NonApprovalStoreDto {

    private String username;
    private String storeRegNum;
    private Long fileId;
    private Long memberId;
    private String uploadFileName;

    public NonApprovalStoreDto() {
    }

    public NonApprovalStoreDto(String username, String storeRegNum, Long fileId, String uploadFileName, Long memberId) {
        this.username = username;
        this.storeRegNum = storeRegNum;
        this.fileId = fileId;
        this.uploadFileName = uploadFileName;
        this.memberId = memberId;
    }
}

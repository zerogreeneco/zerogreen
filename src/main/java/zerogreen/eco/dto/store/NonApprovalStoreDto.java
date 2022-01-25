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
    private String storeRegNum;
    private String uploadFileName;

    public NonApprovalStoreDto() {
    }

    public NonApprovalStoreDto(String username, Long memberId, String storeRegNum, Long fileId, String uploadFileName) {
        this.username = username;
        this.storeRegNum = storeRegNum;
        this.fileId = fileId;
        this.uploadFileName = uploadFileName;
        this.memberId = memberId;
    }
}

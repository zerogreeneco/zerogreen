package zerogreen.eco.dto.store;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NonApprovalStoreDto {

    private String username;
    private String storeRegNum;

    public NonApprovalStoreDto(String username, String storeRegNum) {
        this.username = username;
        this.storeRegNum = storeRegNum;
    }
}

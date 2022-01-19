package zerogreen.eco.entity.userentity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
public class StoreInfo {
    private String storeName;
    private String storeAddress;
    private String storePhoneNumber;
    private String storeDescription;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;

    protected StoreInfo() {}

    public StoreInfo(String storeName, String storeAddress, String storePhoneNumber, LocalDateTime openTime, LocalDateTime closeTime) {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storePhoneNumber = storePhoneNumber;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}

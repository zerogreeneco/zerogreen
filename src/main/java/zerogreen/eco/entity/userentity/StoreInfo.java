package zerogreen.eco.entity.userentity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Embeddable
@Getter
public class StoreInfo {
    private String storeName;
    private String storeAddress;
    private String storePhoneNumber;
    @Lob
    private String storeDescription;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;

    protected StoreInfo() {}

    public StoreInfo(String storeName, String storeAddress, String storePhoneNumber, String storeDescription,LocalDateTime openTime, LocalDateTime closeTime) {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storePhoneNumber = storePhoneNumber;
        this.storeDescription = storeDescription;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}

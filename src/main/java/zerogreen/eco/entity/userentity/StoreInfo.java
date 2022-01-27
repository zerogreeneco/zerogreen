package zerogreen.eco.entity.userentity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public class StoreInfo {

    private String postalCode;
    private String storeAddress;
    private String storeDetailAddress;
    private String storePhoneNumber;
    @Lob
    private String storeDescription;
    private String socialAddress1;
    private String socialAddress2;
    private String openTime;
    private String closeTime;

    protected StoreInfo() {}

    public StoreInfo(String postalCode, String storeAddress, String StoreDetailAddress, String storePhoneNumber) {
        this.postalCode = postalCode;
        this.storeAddress = storeAddress;
        this.storeDetailAddress = StoreDetailAddress;
        this.storePhoneNumber = storePhoneNumber;
    }

    public StoreInfo(String storeAddress, String storePhoneNumber, String storeDescription, String openTime, String closeTime) {
        this.storeAddress = storeAddress;
        this.storePhoneNumber = storePhoneNumber;
        this.storeDescription = storeDescription;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}

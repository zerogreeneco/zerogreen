package zerogreen.eco.entity.userentity;

public enum StoreType {
    FOOD("비건 식당 또는 제로 웨이스트 포장 식당"), ECO_SHOP("친환경 용품가게");

    private final String storeType;

    StoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getStoreType() {
        return storeType;
    }
}

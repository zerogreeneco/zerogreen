package zerogreen.eco.dto.search;

public enum StoreSearchType {
    store_name("가게 이름"), item("메뉴/상품");

    private final String typeName;

    StoreSearchType(String typeName){
        this.typeName = typeName;
    }
}
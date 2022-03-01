package zerogreen.eco.dto.search;

public enum SearchType {
    CONTENT("내용"), WRITER("작성자"),
    STORE_REG_NUM("사업자 등록번호"), STORE_NAME("사업장명");

    private final String searchName;

    SearchType(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchName() {
        return searchName;
    }
}

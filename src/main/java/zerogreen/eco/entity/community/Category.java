package zerogreen.eco.entity.community;

import lombok.Getter;

@Getter
public enum Category {
    QNA("Q&A"), VEGAN("비건"), NEWS("동네 소식"),
    PLOGGING("줍깅"), BEACH_COMBING("비치코밍");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}

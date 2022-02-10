package zerogreen.eco.dto.search;

import lombok.Getter;

@Getter
public class SearchCondition {
    private String content;
    private SearchType searchType;

    public SearchCondition(String content, SearchType searchType) {
        this.content = content;
        this.searchType = searchType;
    }
}

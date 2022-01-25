package zerogreen.eco.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class RequestPageDto {

    private int page;
    private int size;

    public RequestPageDto() {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
}

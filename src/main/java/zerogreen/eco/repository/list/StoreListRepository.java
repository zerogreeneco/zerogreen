package zerogreen.eco.repository.list;

import org.springframework.data.domain.Slice;
import zerogreen.eco.dto.store.StoreDto;

import java.awt.print.Pageable;

public interface StoreListRepository {
    Slice<StoreDto> getShopList(Pageable pageable);
}

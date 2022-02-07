package zerogreen.eco.repository.list;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import zerogreen.eco.dto.store.StoreDto;

public interface StoreListRepository {
    Slice<StoreDto> getShopList(Pageable pageable);
    Slice<StoreDto> getFoodList(Pageable pageable);
}

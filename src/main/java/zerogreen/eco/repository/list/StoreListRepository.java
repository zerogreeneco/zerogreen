package zerogreen.eco.repository.list;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.userentity.StoreType;

public interface StoreListRepository {
    Slice<StoreDto> getShopList(Pageable pageable);
    Slice<StoreDto> getFoodTypeList(Pageable pageable, StoreType storeType);
}

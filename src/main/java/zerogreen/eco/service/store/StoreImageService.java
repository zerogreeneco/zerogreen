package zerogreen.eco.service.store;

import zerogreen.eco.dto.store.StoreDto;

import java.util.List;

public interface StoreImageService {

    //Image List (Detail)
    List<StoreDto> getImageByStore(Long sno);

}

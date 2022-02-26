package zerogreen.eco.service.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.repository.file.StoreImageFileRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreImageServiceImpl implements StoreImageService{

    private final StoreImageFileRepository storeImageFileRepository;

//    @Override
//    public StoreDto getThumbnail(Long sno) {
//        StoreImageFile storeImageFile = storeImageFileRepository.getThumbnail(sno);
//        log.info("imageimageimageimage" + storeImageFile);
//        return new StoreDto(storeImageFile);
//    }

}

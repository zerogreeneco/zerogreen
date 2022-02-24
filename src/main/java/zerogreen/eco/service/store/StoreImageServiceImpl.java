package zerogreen.eco.service.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.repository.file.StoreImageFileRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreImageServiceImpl implements StoreImageService{

    private final StoreImageFileRepository storeImageFileRepository;

    //Image List (Detail)
    @Override
    public List<StoreDto> getImageByStore(Long sno) {
        return storeImageFileRepository.getImageByStore(sno).stream().map(StoreDto::new).collect(Collectors.toList());
    }

}
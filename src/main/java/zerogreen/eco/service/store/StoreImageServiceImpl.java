package zerogreen.eco.service.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.repository.file.StoreImageFileRepository;

import java.io.File;
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
  
//    @Override
//    public StoreDto getThumbnail(Long sno) {
//        StoreImageFile storeImageFile = storeImageFileRepository.getThumbnail(sno);
//        log.info("imageimageimageimage" + storeImageFile);
//        return new StoreDto(storeImageFile);
//    }

    @Override
    public void deleteImg(Long id, String filePath, String thumbFile) {
        File file = new File(filePath);
        File thumb = new File(thumbFile);
        try{
            if(file.exists()){
                boolean delete = file.delete();
                if(delete){
                    thumb.delete();
                    storeImageFileRepository.deleteById(id);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            throw  new IllegalStateException("파일 삭제 실패");
        }
    }
}

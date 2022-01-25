package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import zerogreen.eco.dto.PagingDto;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.repository.file.RegisterFileRepository;
import zerogreen.eco.service.file.FileService;
import zerogreen.eco.service.user.BasicUserService;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final BasicUserService basicUserService;
    private final FileService fileService;
    private final RegisterFileRepository registerFileRepository;

    @GetMapping("/approvalStore")
    public Page<NonApprovalStoreDto> approvalStore(Model model, Pageable pageable) {

        Page<NonApprovalStoreDto> nonApprovalStore = basicUserService.findByNonApprovalStore(pageable);
        long totalListCount = nonApprovalStore.getTotalElements();

        PagingDto pagingDto = new PagingDto(nonApprovalStore);
        log.info("GETPAGE={}",pageable.getPageNumber());
        log.info("TOTALPAGE.TOTALPAGE={}", nonApprovalStore.getTotalPages());
        log.info("TOTALPAGE.CONTENT={}", nonApprovalStore.getContent());
        log.info("HASNEXT={}", nonApprovalStore.hasNext());

        log.info("DTOLIST={}",pagingDto.getDtoList());
        log.info("DTOLIST.TOTALPAGE={}",pagingDto.getTotalPage());
        log.info("DTOLIST.PAGELIST={}",pagingDto.getPageList());

        model.addAttribute("result", pagingDto);



        return nonApprovalStore;
    }

    /*
     * 첨부 파일 다운로드
     * */
    @GetMapping("/approvalStore/attach/{fileId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable("fileId") Long fileId) throws MalformedURLException {

        RegisterFile registerFile = registerFileRepository.findById(fileId).orElseThrow();
        String uploadFileName = registerFile.getUploadFileName();
        String storeFileName = registerFile.getStoreFileName();

        UrlResource urlResource = new UrlResource("file:" + fileService.getFullPath(storeFileName));

        log.info("UPLOADFILENAME={}", uploadFileName);
        log.info("UrlResource={}", urlResource);

        String encodeUploadFilename = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8); // 한글로 된 파일이름이 꺠질 수 있기 때문에
        // CONTENT_DISPOSITION : HttpBody에 들어오는 컨텐츠의 성향을 알려주는 속성
        // attachment; filename="파일명"은 body에 오는 값을 다운로드 받아라는 의미
        String contentDisposition = "attachment; filename=\"" + encodeUploadFilename + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }

    /*
     * UNSTORE -> STORE 권한 변경
     * */
    @PostMapping("/approve")
    @ResponseBody
    public String changeUserRole(@RequestParam("memberId") List<Long> memberId) {

        basicUserService.changeStoreUserRole(memberId);
        return "redirect:/admin/approvalStore";

    }
}

package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import zerogreen.eco.dto.paging.PagingDto;
import zerogreen.eco.dto.paging.RequestPageDto;
import zerogreen.eco.dto.paging.RequestPageSortDto;
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
    public Page<NonApprovalStoreDto> approvalStore(Model model, RequestPageSortDto requestPageDto) {

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("memberId").descending());

        Page<NonApprovalStoreDto> nonApprovalStore = basicUserService.findByNonApprovalStore(pageable);

        PagingDto result = new PagingDto(nonApprovalStore);

        model.addAttribute("result", result);

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
    public ResponseEntity<String> changeUserRole(@RequestParam(value = "memberId", defaultValue = "0") List<Long> memberId, Model model) {

        if (memberId.contains(0L)) {
            model.addAttribute("msg", "회원가입을 승인할 회원을 체크해주세요.");
            return new ResponseEntity<>("fail", HttpStatus.OK);
        }

        basicUserService.changeStoreUserRole(memberId);
        model.addAttribute("msg", "승인이 완료되었습니다. ");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/search")
    public void searchNonApprovalStore(@Validated @ModelAttribute("search") NonApprovalStoreDto searchCond,
                                       BindingResult bindingResult, RequestPageSortDto requestPageDto) {

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("memberID").descending());

        basicUserService.nonApprovalStoreSearch(searchCond, pageable);
    }
}

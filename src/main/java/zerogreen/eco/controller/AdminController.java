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
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.file.RegisterFileRepository;
import zerogreen.eco.service.file.FileService;
import zerogreen.eco.service.user.BasicUserService;
import zerogreen.eco.service.user.StoreMemberService;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("createdDate").descending());

        Page<NonApprovalStoreDto> nonApprovalStore = basicUserService.findByNonApprovalStore(pageable);

        PagingDto result = new PagingDto(nonApprovalStore);

        model.addAttribute("result", result);

        return nonApprovalStore;
    }

    /*
     * ?????? ?????? ????????????
     * */
    @GetMapping("/approvalStore/attach/{fileId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable("fileId") Long fileId) throws MalformedURLException {

        RegisterFile registerFile = registerFileRepository.findById(fileId).orElseThrow();
        String uploadFileName = registerFile.getUploadFileName();
        String storeFileName = registerFile.getStoreFileName();

        UrlResource urlResource = new UrlResource("file:" + fileService.getFullPath(storeFileName));

        String encodeUploadFilename = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8); // ????????? ??? ??????????????? ?????? ??? ?????? ?????????
        // CONTENT_DISPOSITION : HttpBody??? ???????????? ???????????? ????????? ???????????? ??????
        // attachment; filename="?????????"??? body??? ?????? ?????? ???????????? ???????????? ??????
        String contentDisposition = "attachment; filename=\"" + encodeUploadFilename + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }

//    /*
//     * UNSTORE -> STORE ?????? ??????
//     * */
//    @PostMapping("/approve")
//    @ResponseBody
//    public ResponseEntity<Map<String, String>> changeUserRole(@RequestParam(value = "memberId[]", defaultValue = "0") List<Long> memberId) {
//        Map<String, String> resultMap = new HashMap<>();
//        if (memberId.contains(0L)) {
//            resultMap.put("result", "fail");
//            return new ResponseEntity<>(resultMap, HttpStatus.OK);
//        }
//        log.info("APPROVAL MEMBERID LIST={}", memberId);
//        log.info("APPROVAL MEMBERID LIST={}", memberId.size());
//        basicUserService.changeStoreUserRole(memberId);
//        resultMap.put("result", "success");
//        return new ResponseEntity<>(resultMap, HttpStatus.OK);
//    }

    /*
     * UNSTORE -> STORE ?????? ??????
     * */
    @PostMapping("/approve")
    public String changeUserRole(@RequestParam(value = "memberId[]", defaultValue = "0") List<Long> memberId, Model model, RequestPageSortDto requestPageDto) {

        if (memberId.contains(0L)) {
            model.addAttribute("msg", "??????????????? ????????? ????????? ??????????????????.");
            return "admin/approvalStore";
        }

        basicUserService.changeStoreUserRole(memberId);
        model.addAttribute("msg", "????????? ?????????????????????. ");
        Pageable pageable = requestPageDto.getPageableSort(Sort.by("createdDate").descending());

        Page<NonApprovalStoreDto> nonApprovalStore = basicUserService.findByNonApprovalStore(pageable);

        PagingDto result = new PagingDto(nonApprovalStore);

        model.addAttribute("result", result);
        return "admin/approvalStore :: #approval-list";
    }

    @GetMapping("/search")
    public void searchNonApprovalStore(@Validated @ModelAttribute("search") NonApprovalStoreDto searchCond,
                                       BindingResult bindingResult, RequestPageSortDto requestPageDto) {

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("memberID").descending());

        basicUserService.nonApprovalStoreSearch(searchCond, pageable);
    }
}

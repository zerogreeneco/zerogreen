package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.repository.file.RegisterFileRepository;
import zerogreen.eco.repository.user.StoreMemberRepository;
import zerogreen.eco.service.file.FileService;
import zerogreen.eco.service.user.BasicUserService;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final BasicUserService basicUserService;
    private final FileService fileService;
    private final RegisterFileRepository registerFileRepository;

    @GetMapping("/approvalStore")
    public String approvalStore(Model model) {

        List<NonApprovalStoreDto> byNonApprovalStore = basicUserService.findByNonApprovalStore();
        for (NonApprovalStoreDto nonApprovalStoreDto : byNonApprovalStore) {
            log.info("nonApprovalStoreDto.REGNUM = {}" , nonApprovalStoreDto.getStoreRegNum());
            log.info("nonApprovalStoreDto.ID = {}" , nonApprovalStoreDto.getId());
            log.info("nonApprovalStoreDto.NAME = {}" , nonApprovalStoreDto.getUsername());
            log.info("nonApprovalStoreDto.FILENAME = {}" , nonApprovalStoreDto.getUploadFileName());
            log.info("===============================================================");
        }

        model.addAttribute("stores", byNonApprovalStore);

        return "admin/approvalStore";
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
        String contentDisposition = "attachment; filename=\"" + encodeUploadFilename + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}

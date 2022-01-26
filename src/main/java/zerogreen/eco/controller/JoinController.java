package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import zerogreen.eco.dto.file.FileForm;
import zerogreen.eco.dto.member.MemberJoinDto;
import zerogreen.eco.dto.store.StoreJoinDto;
import zerogreen.eco.entity.file.RegisterFile;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.service.file.FileService;
import zerogreen.eco.service.mail.MailService;
import zerogreen.eco.service.user.MemberService;
import zerogreen.eco.service.user.StoreMemberService;

import java.io.IOException;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("/members")
@RequiredArgsConstructor
public class JoinController {

    @Value("${file.dir}")
    private String fileDir;

    @Value("C:/imageUpload/")
    private String imageFileDir;

    private final MemberService memberService;
    private final StoreMemberService storeMemberService;
    private final FileService fileService;
    private final MailService mailService;

    // 컨트롤러 전체에서 사용 가능 (vegan, storeType)
    @ModelAttribute("vegan")
    private VegetarianGrade[] vegetarianGrades() {
        VegetarianGrade[] vegetarianGrades = VegetarianGrade.values();
        return vegetarianGrades;
    }

    @ModelAttribute("storeTypes")
    private StoreType[] storeTypes() {
        StoreType[] storeTypes = StoreType.values();
        return storeTypes;
    }

    /*
    * 일반 회원 가입
    * */
    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") MemberJoinDto member, Model model) {

        return "register/registerForm";
    }

    @PostMapping("/add")
    public String addMember(@Validated @ModelAttribute("member") MemberJoinDto member, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        log.info("member={}", member);

        // 유효성 검사
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError allError : allErrors) {
                log.info("allError ={} ", allError);
            }
            return "register/registerForm";
        }

        // 회원가입 성공 로직
        Member joinMember = member.toMember(member);
        memberService.saveV2(joinMember);
        log.info("joinMember={}", joinMember);

        redirectAttributes.addAttribute("nickname", joinMember.getNickname());
        return "redirect:/members/welcome";
    }

    /*
     * 이메일 인증
     * */
    @PostMapping("/checkMail")
    @ResponseBody
    public HashMap<String, String> sendMail(String mail) {

        HashMap<String, String> keyMap = new HashMap<>();

        log.info("이메일 인증 컨트롤러 OK");
        log.info("EMAIL ={}", mail);
        String key = mailService.createAuthKey();

        keyMap.put("key", key);
        log.info("Before Send Key={}", key);
        mailService.sendAuthMail(mail, key);
        log.info("key={}", key);

        return keyMap;
    }

    /*
    * 환영 페이지
    * */
    @GetMapping("/welcome")
    public String welcome(@RequestParam("nickname") String nickname, Model model) {

        model.addAttribute("nickname", nickname);
        return "register/welcome";
    }

    /*
    * 가게 회원 가입
    * */
    @GetMapping("/store/add")
    public String storeAddForm(@ModelAttribute("store")StoreJoinDto storeJoinDto, @ModelAttribute("file")FileForm fileForm) {
        return "register/storeRegisterForm";
    }

    @PostMapping("/store/add")
    public String storeAdd(@RequestBody @Validated @ModelAttribute("store") StoreJoinDto storeJoinDto, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) throws IOException {

        // 기본 유효성 검사 (Null 체크 등)
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();

            for (ObjectError allError : allErrors) {
                log.info("ERRORCODE={}", allError);
            }
            return "register/storeRegisterForm";
        }

        MultipartFile attachFile = storeJoinDto.getAttachFile();

        // 첨부 파일 유효성 검사
        if (attachFile.isEmpty()) {
            bindingResult.reject("attachFile", null);
            return "register/storeRegisterForm";
        } else if (!attachFile.getContentType().startsWith("image")
                && !attachFile.getContentType().equals("application/pdf") ) {
            log.warn("이미지 또는 PDF 파일이 아닙니다.");
            bindingResult.reject("incorrectAttachFile", null);
            return "register/storeRegisterForm";
        }

        // 회원가입 성공 로직
        RegisterFile uploadFile = fileService.saveFile(attachFile);
        log.info("CONTENT TYPE={}", attachFile.getContentType());
        StoreMember storeMember = new StoreJoinDto().toStoreMember(storeJoinDto);

        storeMemberService.save(storeMember, uploadFile);
        redirectAttributes.addAttribute("nickname", storeMember.getStoreName());

        return "redirect:/members/welcome";

    }
}

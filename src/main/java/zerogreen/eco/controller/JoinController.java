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

    private final MemberService memberService;
    private final StoreMemberService storeMemberService;
    private final FileService fileService;
    private final MailService mailService;

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

//        model.addAttribute("vegan", VegetarianGrade.values());
        return "register/registerForm";
    }

    @PostMapping("/add")
    public String addMember(@Validated @ModelAttribute("member") MemberJoinDto member, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        log.info("member={}", member);

        if (bindingResult.hasErrors()) {
//            model.addAttribute("vegan", VegetarianGrade.values());
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError allError : allErrors) {
                log.info("allError ={} ", allError);
            }
            return "register/registerForm";
        }

        Member joinMember = member.toMember(member);
        memberService.saveV2(joinMember);
        log.info("joinMember={}", joinMember);

        redirectAttributes.addAttribute("nickname", joinMember.getNickname());
//        return "redirect:/login";
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

        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();

            for (ObjectError allError : allErrors) {
                log.info("ERRORCODE={}", allError);
            }
            return "register/storeRegisterForm";
        }

        log.info("STOREJOIN={}", storeJoinDto.getPostalCode());
        log.info("STOREJOIN={}", storeJoinDto.getStoreAddress());

        // 첨부파일 없이 넘어올 경우, 다시 회원가입 창으로 이동
        if (storeJoinDto.getAttachFile().isEmpty()) {
            bindingResult.reject("attachFile", "사업자 등록증을 첨부해주세요.");
            return "register/storeRegisterForm";
        } else {

            RegisterFile uploadFile = fileService.saveFile(storeJoinDto.getAttachFile());
            StoreMember storeMember = new StoreJoinDto().toStoreMember(storeJoinDto);

            storeMemberService.save(storeMember, uploadFile);
            redirectAttributes.addAttribute("nickname", storeMember.getStoreName());

            return "redirect:/members/welcome";
        }

    }
}

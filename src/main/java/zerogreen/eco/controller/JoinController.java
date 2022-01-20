package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") MemberJoinDto member, Model model) {

//        model.addAttribute("vegan", VegetarianGrade.values());
        return "register/registerForm";
    }

    @PostMapping("/add")
    public String addMember(@Validated @ModelAttribute("member") MemberJoinDto member, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {

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
        Long saveMember = memberService.saveV2(joinMember);
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

    @GetMapping("/welcome")
    public String welcome(@RequestParam("nickname") String nickname, Model model) {

        model.addAttribute("nickname", nickname);

        return "register/welcome";
    }

    @GetMapping("/store/add")
    public String storeAddForm(@ModelAttribute("store")StoreJoinDto storeJoinDto, @ModelAttribute("file")FileForm fileForm) {

        return "register/storeRegisterForm";
    }

    @PostMapping("/store/add")
    public String storeAdd(@Validated @ModelAttribute("store") StoreJoinDto storeJoinDto, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();

            for (ObjectError allError : allErrors) {
                log.info("ERROR={}", allError);
            }

        }

        RegisterFile uploadFile = fileService.saveFile(storeJoinDto.getAttachFile());
        StoreMember storeMember = new StoreJoinDto().toStoreMember(storeJoinDto);

        log.info("filePath={}",uploadFile.getFilePath());
        log.info("fileName={}",uploadFile.getStoreFileName());
        log.info("fileName2={}",uploadFile.getUploadFileName());

        storeMemberService.save(storeMember, uploadFile);

        return "register/welcome";
    }
}

package zerogreen.eco.service.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements  MailService {

    private final JavaMailSender javaMailSender;

    @Override
    public String createAuthKey() {
        Random random = new Random(); // 난수 생성
        String key = "";

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(25) + 65; // A~Z 랜덤 알파벳
            key += (char) index;
        }

        int numIndex = random.nextInt(8999) + 1000; // 4자리 랜덤 정수
        key += numIndex;
        return key;
    }

    /*
     * 회원가입 인증 메일 발신
     * */
    @Override
    public void sendAuthMail(String mail, String key) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail); // View에서 보낸 가입 이메일 주소

        message.setSubject("ZEROGREEN 회원 가입을 위한 인증번호 메일");
        message.setText("인증번호 : " + key);

        javaMailSender.send(message);
    }
}

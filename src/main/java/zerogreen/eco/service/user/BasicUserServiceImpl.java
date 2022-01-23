package zerogreen.eco.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.member.FindMemberDto;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.user.BasicUserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasicUserServiceImpl implements BasicUserService{

    private final BasicUserRepository basicUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long adminSave() {

        String encPassword = passwordEncoder.encode("1");

        return basicUserRepository.save(new BasicUser("ADMIN", null, encPassword,UserRole.ADMIN, true))
                .getId();
    }

    /*
    * 인증 받지 못한 가게 회원
    * */
    @Override
    public List<NonApprovalStoreDto> findByNonApprovalStore() {
        return basicUserRepository.findByUnApprovalStore();
    }

    @Override
    public FindMemberDto findByUsernameAndPhoneNumber(String username, String phoneNumber) {
        BasicUser basicUser = basicUserRepository.findByUsernameAndPhoneNumber(username, phoneNumber).orElseThrow();
        return new FindMemberDto(basicUser.getUsername(), basicUser.getPhoneNumber());
    }

    @Override
    public FindMemberDto findByPhoneNumber(String phoneNumber) {
        BasicUser basicUser = basicUserRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        log.info("BasicUser={}", basicUser.getUsername());
        return new FindMemberDto(basicUser.getUsername());
    }

    @Override
    public long countByPhoneNumber(String phoneNumber) {
        log.info("SERVICE COUNT={}",phoneNumber);
        return basicUserRepository.countByPhoneNumber(phoneNumber);
    }

    @Override
    public long countByUsername(String username) {
        return basicUserRepository.countByUsername(username);
    }

    /*
    * UNSTORE -> STORE
    * */
    @Override
    @Transactional
    public void changeStoreUserRole(List<Long> memberId) {
        basicUserRepository.changeUserRole(memberId);
    }
}

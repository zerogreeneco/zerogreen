package zerogreen.eco.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.user.BasicUserRepository;

import java.util.List;

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

    @Override
    public List<NonApprovalStoreDto> findByNonApprovalStore() {
        return basicUserRepository.findByUnApprovalStore();
    }
}

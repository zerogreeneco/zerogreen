package zerogreen.eco.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.repository.BasicUserRepository;

// 시큐리티 설정에서 loginProcessingUrl("/login")으로 설정
// login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는 loadByUsername이 실행
@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private BasicUserRepository basicUserRepository;

    // 시큐리티 세션 (Authentication (UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        BasicUser findUser = basicUserRepository.findByUsername(username);

        if (findUser != null) {
            log.info("로그인 정보 >>> " + findUser);
            return new PrincipalDetails(findUser);
        }
        return null;
    }
}


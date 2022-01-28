package zerogreen.eco.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.security.auth.PrincipalDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public LoginSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)
            throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        List<String> roleList = new ArrayList<>();

        principalDetails.getAuthorities().forEach(new Consumer<GrantedAuthority>() {
            @Override
            public void accept(GrantedAuthority grantedAuthority) {
                roleList.add(grantedAuthority.getAuthority());
                log.info("ROLELIST={}", grantedAuthority.getAuthority());
            }
        });

        HttpSession session = request.getSession();

        if (session != null) {
            String redirectUrl = (String) session.getAttribute("redirectUrl");
            if(roleList.contains(String.valueOf(UserRole.USER))) redirectUrl = "/";
            if (redirectUrl != null) {
                session.removeAttribute("redirectUrl");
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        }

    }
}

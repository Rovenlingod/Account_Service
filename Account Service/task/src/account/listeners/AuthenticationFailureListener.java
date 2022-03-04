package account.listeners;

import account.services.LoginAttemptService;
import account.utilities.CustomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;

@Component
public class AuthenticationFailureListener implements
        ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private HttpServletRequest request;
    private LoginAttemptService loginAttemptService;

    public AuthenticationFailureListener(HttpServletRequest request, LoginAttemptService loginAttemptService) {
        this.request = request;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader.startsWith("Basic")) {
            String userEmail = new String(Base64.getDecoder().decode(authHeader.substring(6)))
                    .replaceAll("(:.*)$", "");
            loginAttemptService.loginFailed(userEmail);
        }
    }
}

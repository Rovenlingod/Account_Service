package account.listeners;

import account.services.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Component
public class AuthenticationSuccessEventListener implements
        ApplicationListener<AuthenticationSuccessEvent> {

    private HttpServletRequest request;
    private LoginAttemptService loginAttemptService;

    public AuthenticationSuccessEventListener(HttpServletRequest request, LoginAttemptService loginAttemptService) {
        this.request = request;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent e) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader.startsWith("Basic")) {
            String userEmail = new String(Base64.getDecoder().decode(authHeader.substring(6)))
                    .replaceAll("(:.*)$", "");
            loginAttemptService.loginSucceeded(userEmail);
        }
    }
}

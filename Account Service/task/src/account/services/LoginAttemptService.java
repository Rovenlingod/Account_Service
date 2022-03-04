package account.services;

public interface LoginAttemptService {

    void loginSucceeded(String email);
    void loginFailed(String email);
    boolean isBlocked(String email);
}

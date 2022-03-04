package account.repositories;

import account.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailIgnoreCase(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailIgnoreCase(String email);
    void deleteByEmail(String email);
    @Query(value = "SELECT u.accountNonLocked FROM User u WHERE u.email = ?1")
    boolean findAccountNonLockedByEmail(String email);
    @Query("UPDATE User u SET u.failedAttempts = u.failedAttempts + 1 WHERE LOWER(u.email) LIKE LOWER(?1)")
    @Modifying
    void incFailedAttemptsIgnoreCase(String email);
    @Query("UPDATE User u SET u.failedAttempts = 0 WHERE LOWER(u.email) LIKE LOWER(?1)")
    @Modifying
    void clearFailedAttemptsIgnoreCase(String email);
}

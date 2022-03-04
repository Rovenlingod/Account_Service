package account.dtos;

import account.domain.LockOperation;

public class UpdateUserLockStatusDTO {

    private String user;
    private LockOperation operation;

    public UpdateUserLockStatusDTO() {
    }

    public UpdateUserLockStatusDTO(String user, LockOperation operation) {
        this.user = user;
        this.operation = operation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LockOperation getOperation() {
        return operation;
    }

    public void setOperation(LockOperation operation) {
        this.operation = operation;
    }
}

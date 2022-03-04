package account.dtos;

import account.domain.AdminOperation;

public class UpdateRoleDTO {
    private String user;
    private String Role;
    private AdminOperation operation;

    public UpdateRoleDTO() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public AdminOperation getOperation() {
        return operation;
    }

    public void setOperation(AdminOperation operation) {
        this.operation = operation;
    }
}

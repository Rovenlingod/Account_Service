package account.dtos;

public class SuccessMessage {

    private String status;

    public SuccessMessage() {
    }

    public SuccessMessage(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

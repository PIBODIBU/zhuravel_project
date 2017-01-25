package main.model;

public class ErrorStatus {
    private Boolean error;
    private String errorMessage;

    public ErrorStatus() {
    }

    public ErrorStatus(Boolean error) {
        this.error = error;
        this.errorMessage = "";
    }

    public ErrorStatus(Boolean error, String errorMessage) {
        this.error = error;
        this.errorMessage = errorMessage;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

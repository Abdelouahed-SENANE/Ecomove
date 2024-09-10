package ma.youcode.transport.enums;

public enum AuthStatus {
    SUCCESS_LOGIN("You are logged in successfully."),
    SUCCESS_REGISTRATION("You are registered successfully."),
    SUCCESS_UPDATE("Your profile updated successfully."),
    EMAIL_ALREADY_REGISTERED("You are already registered with this email."),
    FAILED_REGISTRATION("Failed to register. Please try again."),
    FAILED_LOGIN("Failed to log in. Please check your credentials and try again."),
    ERROR("An error occurred. Please try again later.");

    private final String message;

    AuthStatus(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}

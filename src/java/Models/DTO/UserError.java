package Models.DTO;

import java.io.Serializable;

public class UserError implements Serializable {
    private String userNameError;
    private String lastNameError;
    private String passwordError;
    private String duplicateUserName;

    public UserError() {
    }

    public UserError(String userNameError, String lastNameError,
            String passwordError, String duplicateUserName) {
        this.userNameError = userNameError;
        this.lastNameError = lastNameError;
        this.passwordError = passwordError;
        this.duplicateUserName = duplicateUserName;
    }

    public String getUserNameError() {
        return userNameError;
    }

    public void setUserNameError(String userNameError) {
        this.userNameError = userNameError;
    }

    public String getLastNameError() {
        return lastNameError;
    }

    public void setLastNameError(String lastNameError) {
        this.lastNameError = lastNameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getDuplicateUserName() {
        return duplicateUserName;
    }

    public void setDuplicateUserName(String duplicateUserName) {
        this.duplicateUserName = duplicateUserName;
    }
}

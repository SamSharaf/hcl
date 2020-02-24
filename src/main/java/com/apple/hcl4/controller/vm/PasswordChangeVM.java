package com.apple.hcl4.controller.vm;

/**
 * A VM representing a password change required data - current and new password.
 */
public class PasswordChangeVM {

    private String currentPassword;

    private String newPassword;

    public PasswordChangeVM() {
    }

    public PasswordChangeVM(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {

        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

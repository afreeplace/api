package seyfa.afreeplace.entities.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class PasswordRequest {

    @NotBlank()
    @Size(min=6, max= 20)
    private String lastPassword;

    @NotBlank()
    @Size(min = 6, max = 50)
    private String newPassword;

    public PasswordRequest(String lastPassword, String newPassword) {
        this.lastPassword = lastPassword;
        this.newPassword = newPassword;
    }
}

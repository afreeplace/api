package seyfa.afreeplace.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AuthRequest {

    @NotBlank()
    @Email
    @Size(min=8, max= 100)
    private String email;

    @NotBlank()
    @Size(min = 6, max = 50)
    private String password;

    public AuthRequest() {

    }

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}

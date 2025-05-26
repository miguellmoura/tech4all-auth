package auth_tech4all.tech4all.controller;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginDTO {

    String email;
    String password;

    public UserLoginDTO(String email, String password) {
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
}

package auth_tech4all.tech4all.controller;

public class LoginResponse {

    UserLoginResponseDTO userLoginResponseDTO;

    String token;

    public LoginResponse(UserLoginResponseDTO userLoginResponseDTO, String token) {
        this.userLoginResponseDTO = userLoginResponseDTO;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserLoginResponseDTO getUserLoginResponseDTO() {
        return userLoginResponseDTO;
    }

    public void setUserLoginResponseDTO(UserLoginResponseDTO userLoginResponseDTO) {
        this.userLoginResponseDTO = userLoginResponseDTO;
    }
}

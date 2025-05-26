package auth_tech4all.tech4all.controller;

import auth_tech4all.tech4all.model.UserTech4All;
import auth_tech4all.tech4all.security.Jwt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UserTech4All> cadastrarUsuario (@RequestBody UserDTO userDTO) {
        UserTech4All userTech4All = this.userService.cadastrarUsuario(userDTO);
        return new ResponseEntity<>(userTech4All, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody UserLoginDTO userLoginDTO) {
        LoginResponse loginResponse = userService.login(userLoginDTO);
        if (loginResponse == null) {
            return new ResponseEntity<>("Não Autorizado", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(loginResponse.getToken(), HttpStatus.OK);
    }


}

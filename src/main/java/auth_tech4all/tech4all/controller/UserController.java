package auth_tech4all.tech4all.controller;

import auth_tech4all.tech4all.model.UserTech4All;
import auth_tech4all.tech4all.security.Jwt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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
    public ResponseEntity<LoginResponse> login (@RequestBody UserLoginDTO userLoginDTO) {
        LoginResponse loginResponse = userService.login(userLoginDTO);

        if (loginResponse == null) {
            return new ResponseEntity<LoginResponse>((LoginResponse) null, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/auth/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            boolean isValid = userService.isValid(token);
            return ResponseEntity.ok(Collections.singletonMap("valid", isValid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("valid", false));
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UserDTO>> listarUsuarios() {
        List<UserDTO> usuarios = userService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/usuarios/{email}")
    public ResponseEntity<UserDTO> atualizarUsuario(@PathVariable String email, @RequestBody UserDTO userDTO) {
        UserDTO atualizado = userService.atualizarUsuario(email, userDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/usuarios/{email}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String email) {
        userService.deletarUsuario(email);
        return ResponseEntity.noContent().build();
    }


}

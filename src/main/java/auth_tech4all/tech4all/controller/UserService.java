package auth_tech4all.tech4all.controller;

import auth_tech4all.tech4all.model.UserTech4All;
import auth_tech4all.tech4all.security.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final Jwt jwt;
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, Jwt jwt) {
        this.userRepository = userRepository;
        this.jwt = jwt;
    }

    public UserTech4All cadastrarUsuario (UserDTO userDTO) {
        UserTech4All userTech4All = new UserTech4All();
        userTech4All.setNome(userDTO.getNome());
        userTech4All.setRole(userDTO.getRole());
        userTech4All.setContato(userDTO.getContato());
        userTech4All.setEmail(userDTO.getEmail());
        userTech4All.setPassword(userDTO.getPassword());
        userRepository.save(userTech4All);
        return userTech4All;
    }

    public LoginResponse login (UserLoginDTO userLoginDTO) {
        UserTech4All user = userRepository.findByEmail(userLoginDTO.getEmail());
        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        if (!Objects.equals(userLoginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        return new LoginResponse(jwt.createToken(user));
    }

    public boolean isValid (String token) {
        return jwt.isValid(token);
    }

}

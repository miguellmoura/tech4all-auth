package auth_tech4all.tech4all.controller;

import auth_tech4all.tech4all.model.UserTech4All;
import auth_tech4all.tech4all.security.Jwt;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

        System.out.println(user.getNome());

        return new LoginResponse(new UserLoginResponseDTO(user.getNome(), user.getEmail(), user.getContato(), user.getRole()), jwt.createToken(user));
    }

    public boolean isValid (String token) {
        return jwt.isValid(token);
    }

    public List<UserDTO> listarTodos() {
        return userRepository.findAll().stream().map(user -> new UserDTO(
                user.getNome(),
                user.getEmail(),
                user.getContato(),
                null,
                user.getRole()
        )).toList();
    }

    public UserDTO atualizarUsuario(String email, UserDTO userDTO) {
        UserTech4All user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado com e-mail: " + email);
        }

        user.setNome(userDTO.getNome());
        user.setContato(userDTO.getContato());
        user.setRole(userDTO.getRole());
        // Atualiza o e-mail só se for necessário
        user.setEmail(userDTO.getEmail());

        userRepository.save(user);

        return new UserDTO(
                user.getNome(),
                user.getEmail(),
                user.getContato(),
                null, // nunca retornar senha
                user.getRole()
        );
    }

    public void deletarUsuario(String email) {
        UserTech4All user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Usuário não encontrado para exclusão.");
        }
        userRepository.delete(user);
    }




}

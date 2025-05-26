package auth_tech4all.tech4all.controller;

import auth_tech4all.tech4all.model.UserTech4All;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

}

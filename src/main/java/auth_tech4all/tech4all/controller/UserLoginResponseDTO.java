package auth_tech4all.tech4all.controller;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginResponseDTO {

    private String nome;

    private String email;

    private String contato;

    private UserRole role;

    public UserLoginResponseDTO () {}

    public UserLoginResponseDTO(String nome, String email, String contato, UserRole role) {
        this.nome = nome;
        this.email = email;
        this.contato = contato;
        this.role = role;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}

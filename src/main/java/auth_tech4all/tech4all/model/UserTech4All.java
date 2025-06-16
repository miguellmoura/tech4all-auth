package auth_tech4all.tech4all.model;

import auth_tech4all.tech4all.controller.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

@Table(name = "\"users\"")
public class UserTech4All {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ou AUTO, SEQUENCE, TABLE, conforme banco
    private Long id;

    private String nome;

    private String email;

    private String contato;

    private String password;

    private UserRole role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}

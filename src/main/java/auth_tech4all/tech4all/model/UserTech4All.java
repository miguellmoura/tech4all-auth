package auth_tech4all.tech4all.model;

import auth_tech4all.tech4all.controller.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "[usuarios]") // 🔄 Evita conflito com "users"
public class UserTech4All {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String contato;

    private String password;

    @Enumerated(EnumType.STRING) // ✅ Salva como texto: MASTER, ADMIN, USER
    private UserRole role;
}

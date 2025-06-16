package auth_tech4all.tech4all.controller;

import auth_tech4all.tech4all.model.UserTech4All;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <UserTech4All, Long> {
    public UserTech4All findByEmail(String email);
}

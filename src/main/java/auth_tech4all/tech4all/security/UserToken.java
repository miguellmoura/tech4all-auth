package auth_tech4all.tech4all.security;

import auth_tech4all.tech4all.model.UserTech4All;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class UserToken {
    private Long id;
    private String name;
    private Set<String> roles;

    public UserToken() {
        this.id = 0L;
        this.name = "";
        this.roles = new TreeSet<>();
    }

    public UserToken(UserTech4All userTech4All) {
        this.id = userTech4All.getId() != null ? userTech4All.getId() : -1L;
        this.name = userTech4All.getNome();
        this.roles = new TreeSet<>();
        if (userTech4All.getRole() != null) {
            this.roles.add(userTech4All.getRole().name());
        }
    }

    public UserToken(Long id, String name, Set<String> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<String> getRoles() {
        return roles;
    }

    @JsonIgnore
    public boolean isAdmin() {
        return roles.contains("ADMIN");
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    private Map<String, Object> userTokenToMap(UserToken token) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", token.getId());
        map.put("name", token.getName());
        map.put("roles", token.getRoles());
        map.put("admin", token.isAdmin());
        return map;
    }
}

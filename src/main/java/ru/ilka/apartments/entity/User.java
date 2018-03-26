package ru.ilka.apartments.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "USERS")
public class User implements IDatabaseEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "BAN")
    private boolean ban;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role = Role.GUEST;

    @Column(name = "ENABLED", columnDefinition = "boolean default true", nullable = false)
    private boolean enabled;

    /*  LAZY = fetch when needed
    *   EAGER = fetch immediately
    */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_HAS_APARTMENTS",
            joinColumns = @JoinColumn(name = "USERS_FK", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "APARTMENTS_FK", referencedColumnName = "ID"))
    @JsonIgnore
    private Set<Apartment> apartments;

    public User() {
        this.apartments = new HashSet<>();
    }

    public User(int id, String login, String password, boolean ban) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.ban = ban;
        this.enabled = !ban;
        this.apartments = new HashSet<>();
    }

    public User(User user) {
        this.id = user.id;
        this.login = user.login;
        this.password = user.password;
        this.ban = user.ban;
        this.apartments = user.apartments;
        this.role = user.role;
        this.enabled = user.enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
        this.enabled = !ban;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(Set<Apartment> apartments) {
        this.apartments = apartments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (ban != user.ban) return false;
        if (!login.equals(user.login)) return false;
        return password != null ? password.equals(user.password) : user.password == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + login.hashCode();
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (ban ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", ban=" + ban +
                ", role=" + role +
                '}';
    }
}

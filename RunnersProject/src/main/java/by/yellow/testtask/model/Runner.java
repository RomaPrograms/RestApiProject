package by.yellow.testtask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "runners")
public class Runner extends BaseEntity {

    @Column(name = "login")
    private String login;
    @JsonIgnore
    @Column(name = "password")
    private String password;

    public Runner() {
    }

    public Runner(@JsonProperty("login") String login,
                  @JsonProperty("password") String password) {
        this.login = login;
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Runner)) return false;
        if (!super.equals(o)) return false;
        Runner runner = (Runner) o;
        return Objects.equals(getLogin(), runner.getLogin()) &&
                Objects.equals(getPassword(), runner.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLogin(), getPassword());
    }
}

package by.yellow.testtask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "runners")
@Data
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
}

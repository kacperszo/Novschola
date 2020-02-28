package io.novschola.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * User business model class
 * @author Kacper Szot
 */
@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @UniqueElements
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    @Lob
    private String bio;
    @CreationTimestamp
    private LocalDateTime createDate;
    private boolean active;
    @ManyToOne
    private SchoolClass schoolClass;
    private String activationKey;
    @ManyToMany
    private List<Role> roles = new ArrayList<>();

    public User(String email, String firstName, String lastName, String password){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

}

package io.novschola.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String salt;
    @Lob
    private String bio;
    @CreationTimestamp
    private LocalDateTime createDate;
    private boolean active;
    @ManyToOne
    private SchoolClass schoolClass;
    private String activationKey;
    @ElementCollection
    private List<String> roles;
}
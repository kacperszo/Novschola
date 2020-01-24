package io.novschola.model;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @NotNull
    @NotBlank
    private String password;
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

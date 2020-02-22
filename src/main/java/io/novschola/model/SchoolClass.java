package io.novschola.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;
/**
 * School class business model class
 * @author Kacper Szot
 */
@Entity
@Data
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "schoolClass")
    private List<User> students;
}

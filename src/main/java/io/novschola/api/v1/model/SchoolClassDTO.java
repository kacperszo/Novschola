package io.novschola.api.v1.model;

import lombok.Data;

/**
 * School class data transfer object
 * Objects of this class are returned by controllers instead of business' layer models
 * @author Kacper Szot
 */
@Data
public class SchoolClassDTO {
    private Long id;
    private String name;
}

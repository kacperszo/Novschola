package io.novschola.api.v1.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * School class data transfer object
 * Objects of this class are returned by controllers instead of business' layer models
 * @author Kacper Szot
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassResponse {
    private Long id;
    private String name;
}

package com.vicariusapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties({"accessHistoryList", "userQuota"}) // Add this annotation to ignore these fields during serialization
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @OneToMany(mappedBy = "user")
    private List<AccessHistory> accessHistoryList;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserQuota userQuota;
    private LocalDateTime lastLoginTimeUtc;
    private Boolean blocked = false;
}

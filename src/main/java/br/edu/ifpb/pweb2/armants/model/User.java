package br.edu.ifpb.pweb2.armants.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @OneToMany(mappedBy = "username", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Authority> authorities;
    @Id
    @NotBlank(message = "Required Field")
    @Size(min = 3, message = "Size must be at least 3.")
    private String username;
    @NotBlank(message = "Required Field")
    @Size(min = 3, message = "Size must be at least 3.")
    private String password;
    private Boolean enabled = true;
}
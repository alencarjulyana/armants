package br.edu.ifpb.pweb2.armants.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "authorities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @EmbeddedId
    private AuthorityId id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private User username;

    @Column(name = "authority", insertable = false, updatable = false)
    private String authority;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthorityId implements java.io.Serializable {
        private String username;
        private String authority;
    }
}

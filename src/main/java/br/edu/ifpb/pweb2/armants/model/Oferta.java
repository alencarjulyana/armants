package br.edu.ifpb.pweb2.armants.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Oferta {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    private String atividadePrincipal;
    @Setter
    @Getter
    private Integer cargaHorariaSemanal;
    @Setter
    @Getter
    private boolean isDeleted = false;;
    @Setter
    @Getter
    private Double valorPago;
    @Setter
    @Getter
    private Boolean valeTransporte;
    @Setter
    @Getter
    private String preRequisitos;
    @Setter
    @Getter
    private String habilidadesNecessarias;
    @Setter
    @Getter
    private String habilidadesDesejaveis;

    @Setter
    @Getter
    @ManyToOne
    private Empresa empresa;

    @Setter
    @Getter
    @ManyToMany
    @JoinTable(
            name = "oferta_aluno",
            joinColumns = @JoinColumn(name = "oferta_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> candidatos = new ArrayList<>();

    // Getters e Setters

    public void addCandidato(Aluno aluno) {
        this.candidatos.add(aluno);
    }
}

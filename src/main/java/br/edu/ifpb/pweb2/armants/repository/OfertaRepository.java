package br.edu.ifpb.pweb2.armants.repository;

import br.edu.ifpb.pweb2.armants.model.Aluno;
import br.edu.ifpb.pweb2.armants.model.Empresa;
import br.edu.ifpb.pweb2.armants.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {
    List<Oferta> findByEmpresa(Empresa empresa);
    List<Oferta> findByHabilidadesDesejaveisContainingAndHabilidadesNecessariasContainingAndPreRequisitosContaining(
            String habilidadesDesejaveis, String habilidadesNecessarias, String preRequisitos);

    List<Oferta> findByCandidatosContains(Aluno aluno);
}

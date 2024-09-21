package br.edu.ifpb.pweb2.armants.service;

import br.edu.ifpb.pweb2.armants.model.Aluno;
import br.edu.ifpb.pweb2.armants.model.Empresa;
import br.edu.ifpb.pweb2.armants.model.Oferta;
import br.edu.ifpb.pweb2.armants.repository.AlunoRepository;
import br.edu.ifpb.pweb2.armants.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public Oferta save(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    public List<Oferta> findAllByEmpresa(Empresa empresa) {
        return ofertaRepository.findByEmpresa(empresa);
    }

    public void deleteById(Long id) {
        Optional<Oferta> oferta = ofertaRepository.findById(id);
        oferta.ifPresent(value -> value.setDeleted(true));
        oferta.ifPresent(ofertaRepository::save);
    }

    public Optional<Oferta> findById(Long id) {
        return ofertaRepository.findById(id);
    }

    public Page<Oferta> findAll(Pageable pageable) {
        return ofertaRepository.findAll(pageable);
    }
    public void addCandidato(Long ofertaId, Aluno aluno) {
        Optional<Oferta> ofertaOptional = ofertaRepository.findById(ofertaId);
        if (ofertaOptional.isPresent()) {
            Oferta oferta = ofertaOptional.get();
            oferta.addCandidato(aluno);
            ofertaRepository.save(oferta);
        }
    }

    public boolean isAlunoCandidato(Long alunoId, Long ofertaId) {
        Oferta oferta = ofertaRepository.findById(ofertaId).orElse(null);
        assert oferta != null;
        return oferta.getCandidatos().stream()
                .anyMatch(aluno -> aluno.getId().equals(alunoId));
    }

    public List<Oferta> filterOfertas(String habilidadesDesejaveis, String habilidadesNecessarias, String preRequisitos) {
        return ofertaRepository.findByHabilidadesDesejaveisContainingAndHabilidadesNecessariasContainingAndPreRequisitosContaining(
                habilidadesDesejaveis, habilidadesNecessarias, preRequisitos);
    }

    public List<Oferta> findOfertasByAlunoId(Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId).orElse(null);
        if (aluno == null) {
            return Collections.emptyList();
        }
        return ofertaRepository.findByCandidatosContains(aluno);
    }
}

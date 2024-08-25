package br.edu.ifpb.pweb2.armants.service;

import br.edu.ifpb.pweb2.armants.model.Aluno;
import br.edu.ifpb.pweb2.armants.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void save(Aluno aluno) {
        aluno.setSenha(passwordEncoder.encode(aluno.getSenha())); // Encripta a senha
        alunoRepository.save(aluno);
    }

    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> findById(Long id) {
        return alunoRepository.findById(id);
    }


}

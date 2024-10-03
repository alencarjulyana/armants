package br.edu.ifpb.pweb2.armants.service;

import br.edu.ifpb.pweb2.armants.model.Aluno;
import br.edu.ifpb.pweb2.armants.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void save(Aluno aluno) {
        aluno.setSenha(passwordEncoder.encode(aluno.getSenha())); // Encripta a senha
        alunoRepository.save(aluno);
    }

    public Page<Aluno> findAll(Pageable pageable) {
        return alunoRepository.findAll(pageable);
    }

    public Optional<Aluno> findById(Long id) {
        return alunoRepository.findById(id);
    }


}
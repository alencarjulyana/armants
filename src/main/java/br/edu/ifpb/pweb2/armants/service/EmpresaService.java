package br.edu.ifpb.pweb2.armants.service;

import br.edu.ifpb.pweb2.armants.model.Empresa;
import br.edu.ifpb.pweb2.armants.model.Oferta;
import br.edu.ifpb.pweb2.armants.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public boolean cnpjJaExiste(String cnpj) {
        return empresaRepository.findByCnpj(cnpj).isPresent();
    }

    public Optional<Empresa> findByEmail(String email) {
        return empresaRepository.findByEmail(email);
    }


    public void save(Empresa empresa) {
        empresaRepository.save(empresa);
    }

    public Optional<Empresa> findById(Long id) {
        return empresaRepository.findById(id);
    }

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }
}

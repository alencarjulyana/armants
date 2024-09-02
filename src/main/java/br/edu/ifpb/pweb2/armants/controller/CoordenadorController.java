package br.edu.ifpb.pweb2.armants.controller;

import br.edu.ifpb.pweb2.armants.model.Aluno;
import br.edu.ifpb.pweb2.armants.model.Empresa;
import br.edu.ifpb.pweb2.armants.model.Oferta;
import br.edu.ifpb.pweb2.armants.service.AlunoService;
import br.edu.ifpb.pweb2.armants.service.EmpresaService;
import br.edu.ifpb.pweb2.armants.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/coordenador")
public class CoordenadorController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private OfertaService ofertaService;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/alunos-candidatos")
    public String listarAlunosCandidatos(Model model, Pageable pageable) {
        Page<Aluno> alunos = alunoService.findAll(pageable);
        model.addAttribute("alunos", alunos);
        return "coordenador/alunosCandidatos";
    }

    @GetMapping("/alunos-candidatos/{id}")
    public String detalhesAluno(@PathVariable Long id, Model model) {
        Aluno aluno = alunoService.findById(id).orElse(null);
        if (aluno == null) {
            return "redirect:/coordenador/alunos-candidatos";
        }

        List<Oferta> ofertas = ofertaService.findOfertasByAlunoId(aluno.getId());
        model.addAttribute("aluno", aluno);
        model.addAttribute("ofertas", ofertas);

        return "coordenador/detalhesAluno";
    }

    @GetMapping("/ofertas-estagio")
    public String listarOfertasEstagio(Model model, Pageable pageable) {
        Page<Oferta> ofertas = ofertaService.findAll(pageable);
        model.addAttribute("ofertas", ofertas);
        return "coordenador/ofertasEstagio";
    }

    @GetMapping("/ofertas-estagio/{id}")
    public String detalhesOfertaEstagio(@PathVariable Long id, Model model) {
        Oferta oferta = ofertaService.findById(id).orElse(null);
        if (oferta == null) {
            return "redirect:/coordenador/ofertas-estagio";
        }

        Empresa empresa = empresaService.findById(oferta.getEmpresa().getId()).orElse(null);
        model.addAttribute("oferta", oferta);
        model.addAttribute("empresa", empresa);

        return "coordenador/detalhesOferta";
    }

    @GetMapping("/empresas")
    public String listarEmpresas(Model model, Pageable pageable) {
        Page<Empresa> empresas = empresaService.findAll(pageable);
        model.addAttribute("empresas", empresas);
        return "coordenador/listaEmpresas";
    }

    @GetMapping("/empresas/{id}")
    public String detalhesEmpresa(@PathVariable Long id, Model model) {
        Empresa empresa = empresaService.findById(id).orElse(null);
        if (empresa == null) {
            return "redirect:/coordenador/empresas";
        }

        model.addAttribute("empresa", empresa);
        return "coordenador/detalhesEmpresa";
    }

    @GetMapping("/empresas/editar/{id}")
    public String editarEmpresa(@PathVariable Long id, Model model) {
        Empresa empresa = empresaService.findById(id).orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        model.addAttribute("empresas", empresa);
        return "coordenador/editarEmpresas";
    }

    @PostMapping("/empresas/editar/{id}")
    public String salvarEdicaoEmpresa(@PathVariable Long id, @ModelAttribute("empresa") Empresa empresaAtualizada) {
        empresaService.atualizarEmpresa(id, empresaAtualizada);
        return "redirect:/coordenador/listaEmpresas";
    }
}

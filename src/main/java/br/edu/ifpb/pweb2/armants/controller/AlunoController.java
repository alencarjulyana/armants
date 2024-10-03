package br.edu.ifpb.pweb2.armants.controller;

import br.edu.ifpb.pweb2.armants.model.Aluno;
import br.edu.ifpb.pweb2.armants.model.Oferta;
import br.edu.ifpb.pweb2.armants.service.AlunoService;
import br.edu.ifpb.pweb2.armants.service.OfertaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private OfertaService ofertaService;

    @GetMapping("/novo")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "aluno/cadastro";
    }

    @PostMapping("/novo")
    public String cadastrarAluno(@ModelAttribute("aluno") @Valid Aluno aluno, BindingResult result, HttpSession session, Model model) {
        if (result.hasErrors()) {
            return "aluno/cadastro";
        }
        alunoService.save(aluno);
        session.setAttribute("aluno", aluno);
        return "redirect:/aluno/ofertas?habilidadesDesejaveis=&habilidadesNecessarias=&preRequisitos=";
    }

    @GetMapping("/ofertas")
    @PreAuthorize("hasAnyRole('COORDENADOR','EMPRESA','ALUNO')")
    public String listarOfertas(
            @RequestParam(value = "habilidadesDesejaveis", required = false) String habilidadesDesejaveis,
            @RequestParam(value = "habilidadesNecessarias", required = false) String habilidadesNecessarias,
            @RequestParam(value = "preRequisitos", required = false) String preRequisitos,
            @RequestParam(value = "minhasOfertas", defaultValue = "false") boolean minhasOfertas,
            Model model, HttpSession session) {

        Aluno aluno = (Aluno) session.getAttribute("aluno");
        if (aluno == null) {
            return "redirect:/aluno/novo";
        }

        List<Oferta> ofertas;
        if (minhasOfertas) {
            ofertas = ofertaService.findOfertasByAlunoId(aluno.getId());
        } else {
            ofertas = ofertaService.filterOfertas(habilidadesDesejaveis, habilidadesNecessarias, preRequisitos);
        }

        Map<Long, Boolean> statusCandidatura = new HashMap<>();
        for (Oferta oferta : ofertas) {
            boolean jaCandidatou = ofertaService.isAlunoCandidato(aluno.getId(), oferta.getId());
            statusCandidatura.put(oferta.getId(), jaCandidatou);
        }

        model.addAttribute("ofertas", ofertas);
        model.addAttribute("statusCandidatura", statusCandidatura);
        model.addAttribute("habilidadesDesejaveis", habilidadesDesejaveis);
        model.addAttribute("habilidadesNecessarias", habilidadesNecessarias);
        model.addAttribute("preRequisitos", preRequisitos);

        return "aluno/ofertas";
    }

    @GetMapping("/ofertas/{id}")
    @PreAuthorize("hasRole('COORDENADOR','ALUNO')")
    public String mostrarDetalhesOferta(@PathVariable("id") Long id, Model model, HttpSession session) {
        Aluno aluno = (Aluno) session.getAttribute("aluno");
        if (aluno == null) {
            return "redirect:/aluno/cadastro";
        }

        Optional<Oferta> oferta = ofertaService.findById(id);
        if (oferta.isEmpty()) {
            return "redirect:/aluno/ofertas?habilidadesDesejaveis=&habilidadesNecessarias=&preRequisitos=";
        }

        boolean jaCandidatou = ofertaService.isAlunoCandidato(aluno.getId(), oferta.get().getId());

        model.addAttribute("oferta", oferta.get());
        model.addAttribute("jaCandidatou", jaCandidatou);

        return "aluno/detalhesOferta";
    }

    @PostMapping("/ofertas/{id}/candidatar")
    @PreAuthorize("hasRole('COORDENADOR','ALUNO')")
    public String candidatarOferta(@PathVariable("id") Long id, HttpSession session) {
        Aluno aluno = (Aluno) session.getAttribute("aluno");
        if (aluno == null) {
            return "redirect:/aluno/cadastro";
        }

        Optional<Oferta> oferta = ofertaService.findById(id);
        if (oferta.isEmpty()) {
            return "redirect:/aluno/ofertas";
        }

        ofertaService.addCandidato(oferta.get().getId(), aluno);

        return "redirect:/aluno/ofertas?habilidadesDesejaveis=&habilidadesNecessarias=&preRequisitos=";
    }
}


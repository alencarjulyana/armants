package br.edu.ifpb.pweb2.armants.controller;

import br.edu.ifpb.pweb2.armants.model.Empresa;
import br.edu.ifpb.pweb2.armants.model.Oferta;
import br.edu.ifpb.pweb2.armants.service.EmpresaService;
import br.edu.ifpb.pweb2.armants.service.OfertaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/ofertas")
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/nova")
    public String showFormNovaOferta(Model model) {
        model.addAttribute("oferta", new Oferta());
        return "ofertas/cadastro";
    }

    @PostMapping("/nova")
    public String cadastrarOferta(HttpSession session, @ModelAttribute("oferta") @Valid Oferta oferta, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ofertas/cadastro";
        }

        Empresa empresa = (Empresa) session.getAttribute("empresa");



        oferta.setEmpresa(empresa);
        ofertaService.save(oferta);

        return "redirect:/ofertas/lista";
    }

    @GetMapping("/lista")
    public String listarOfertas(Model model, HttpSession session) {
        Empresa empresa = (Empresa) session.getAttribute("empresa");

            model.addAttribute("ofertas", ofertaService.findAllByEmpresa(empresa));
            return "ofertas/lista";

    }

    @PostMapping("/cancelar/{id}")
    public String cancelarOferta(@PathVariable("id") Long id, HttpSession session) {
        Optional<Oferta> oferta = ofertaService.findById(id);
        if (oferta.isPresent()) {
            Empresa empresa = (Empresa) session.getAttribute("empresa");
            if (Objects.equals(oferta.get().getEmpresa().getCnpj(), empresa.getCnpj())) {
                ofertaService.deleteById(id);
            }
        }
        return "redirect:/ofertas/lista";
    }
}

package br.edu.ifpb.pweb2.armants.controller;

import br.edu.ifpb.pweb2.armants.model.Empresa;
import br.edu.ifpb.pweb2.armants.service.EmpresaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/empresas")
@SessionAttributes("empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/novo")
    public String showEmpresaForm(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresas/cadastro";
    }

    @PostMapping("/novo")
    public String cadastrarEmpresa(@ModelAttribute("empresa") @Valid Empresa empresa,
                                   BindingResult result,
                                   Model model,
                                   HttpSession session) {
        if (result.hasErrors()) {
            return "empresas/cadastro";
        }

        if (empresaService.cnpjJaExiste(empresa.getCnpj())) {
            model.addAttribute("cnpjError", "CNPJ já cadastrado.");
            return "empresas/cadastro";
        }

        if (empresa.getDocumentoComprovacao() != null && !empresa.getDocumentoComprovacao().isEmpty()) {
            try {
                byte[] documentoBytes = empresa.getDocumentoComprovacao().getBytes();
                empresa.setDocumentoBytes(documentoBytes);
            } catch (IOException e) {
                e.printStackTrace();
                result.rejectValue("documentoComprovacao", "error.empresa", "Falha ao processar o arquivo.");
                return "empresas/cadastro";
            }
        }

        empresaService.save(empresa);

        session.setAttribute("empresa", empresa);

        return "redirect:/ofertas/lista";
    }

    @GetMapping("/{id}/download-documento")
    @PreAuthorize("hasAnyRole('COORDENADOR','EMPRESA')")
    public ResponseEntity downloadDocumentoComprovacao(@PathVariable("id") Long id) {
        Optional<Empresa> empresa = empresaService.findById(id);

        if (empresa.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);

        }

        if (empresa.get().getDocumentoBytes() != null) {
            HttpHeaders headers = new HttpHeaders();
            String filename = empresa.get().getNome() + "documento_comprovacao.pdf";
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "documento_comprovacao.pdf");
            return new ResponseEntity<>(empresa.get().getDocumentoBytes(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
        }
    }

}
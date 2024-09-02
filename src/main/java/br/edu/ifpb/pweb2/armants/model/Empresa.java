package br.edu.ifpb.pweb2.armants.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Entity
public class Empresa  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nome é obrigatório")
    private String nome;

    @NotEmpty(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve ter 14 dígitos")
    @Column(unique = true)
    private String cnpj;

    @NotEmpty(message = "Endereço é obrigatório")
    private String endereco;

    @NotEmpty(message = "Telefone é obrigatório")
    private String telefone;

    @Email(message = "Email inválido")
    @NotEmpty(message = "Email é obrigatório")
    private String email;

    @NotEmpty(message = "Pessoa de contato é obrigatória")
    private String pessoaContato;

    @NotEmpty(message = "Atividade principal é obrigatória")
    private String atividadePrincipal;

    @NotEmpty(message = "URL é obrigatória")
    private String urlEmpresa;


    @Lob
    private byte[] documentoBytes; // Armazena o PDF

    @Transient
    private MultipartFile documentoComprovacao;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPessoaContato() {
        return pessoaContato;
    }

    public void setPessoaContato(String pessoaContato) {
        this.pessoaContato = pessoaContato;
    }

    public String getAtividadePrincipal() {
        return atividadePrincipal;
    }

    public void setAtividadePrincipal(String atividadePrincipal) {
        this.atividadePrincipal = atividadePrincipal;
    }

    public String getUrlEmpresa() {
        return urlEmpresa;
    }

    public void setUrlEmpresa(String urlEmpresa) {
        this.urlEmpresa = urlEmpresa;
    }


    public byte[] getDocumentoBytes() {
        return documentoBytes;
    }

    public void setDocumentoBytes(byte[] documentoBytes) {
        this.documentoBytes = documentoBytes;
    }

    public MultipartFile getDocumentoComprovacao() {
        return documentoComprovacao;
    }

    public void setDocumentoComprovacao(MultipartFile documentoComprovacao) {
        this.documentoComprovacao = documentoComprovacao;
    }


    public void setAtivo(boolean b) {
    }
}

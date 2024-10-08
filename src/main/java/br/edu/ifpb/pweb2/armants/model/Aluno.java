package br.edu.ifpb.pweb2.armants.model;


import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Setter
@Getter
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nome � obrigat�rio")
    private String nome;

    @NotEmpty(message = "Matr�cula � obrigat�ria")
    @Size(min = 5, max = 10, message = "Matr�cula deve ter entre 5 e 10 caracteres")
    private String matricula;

    @NotEmpty(message = "CPF � obrigat�rio")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 d�gitos")
    private String cpf;

    @NotEmpty(message = "Nome de usu�rio � obrigat�rio")
    private String username;

    @NotEmpty(message = "Senha � obrigat�ria")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String senha;

    // Campo de habilidades como String
    private String habilidades;

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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }
}
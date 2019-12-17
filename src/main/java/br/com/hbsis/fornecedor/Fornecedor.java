package br.com.hbsis.fornecedor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "seg_fornecedores")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "razao_Social", nullable = false)
    private String razaoSocial;
    @Column(name = "cnpj", unique = true, nullable = false)
    private Long cnpj;
    @Column(name = "nome_Fantasia", nullable = false)
    private String nomeFantasia;
    @Column(name = "endereco", nullable = false)
    private String endereco;
    @Column(name = "telefone_contato", nullable = false)
    private Long telefoneContato;
    @Column(name = "email_contato", nullable = false)
    private String emailContato;

    public void setId(Long id) { this.id = id; }

    public Long getId() {
        return id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Long getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(Long telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    public String getEmailContato() {
        return emailContato;
    }

    public void setEmailContato(String emailContato) {
        this.emailContato = emailContato;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id +
                ", Razao social='" + razaoSocial + '\'' +
                ", CNPJ='" + cnpj + '\'' +
                ", Nome Fanstasia='" + nomeFantasia + '\'' +
                ", Endereço='" + endereco + '\'' +
                ", Telefone de contato='" + telefoneContato + '\'' +
                ", E-mail de contato='" + emailContato + '\'' +
                '}';
    }
}


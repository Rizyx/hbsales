package br.com.hbsis.fornecedor;

import javax.persistence.*;

@Entity
@Table(name = "seg_fornecedores")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Razao_Social", nullable = false, length = 100)
    private String Razao_Social;
    @Column(name = "CNPJ", unique = true, nullable = false, length = 30)
    private String CNPJ;
    @Column(name = "Nome_Fantasia", nullable = false, length = 150)
    private String Nome_Fantasia;
    @Column(name = "Endereco", unique = true, length = 200)
    private String Endereco;
    @Column(name = "Telefone_contato", unique = true, length = 20)
    private String Telefone_contato;
    @Column(name = "Email_contato", unique = true, length = 120)
    private String Email_contato;

    public Long getId() {
        return id;
    }

    public String getRazao_Social() {
        return Razao_Social;
    }

    public void setRazao_Social(String razao_Social) {
        this.Razao_Social = razao_Social;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getNome_Fantasia() {
        return Nome_Fantasia;
    }

    public void setNome_Fantasia(String nome_Fantasia) {
        this.Nome_Fantasia = nome_Fantasia;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getTelefone_contato() {
        return Telefone_contato;
    }

    public void setTelefone_contato(String telefone_contato) {
        this.Telefone_contato = telefone_contato;
    }

    public String getEmail_contato() {
        return Email_contato;
    }

    public void setEmail_contato(String email_contato) {
        this.Email_contato = email_contato;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id +
                ", Razao social='" + Razao_Social + '\'' +
                ", CNPJ='" + CNPJ + '\'' +
                ", Nome Fanstasia='" + Nome_Fantasia + '\'' +
                ", Endereço='" + Endereco + '\'' +
                ", Telefone de contato='" + Telefone_contato + '\'' +
                ", E-mail de contato='" + Email_contato + '\'' +
                '}';
    }
}


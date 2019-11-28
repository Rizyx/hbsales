package br.com.hbsis.fornecedor;

import br.com.hbsis.categoriaProduto.CategoriaProduto;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "seg_fornecedores")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_fornecedor;
    @Column(name = "Razao_Social", nullable = false, length = 100)
    private String razaoSocial;
    @Column(name = "CNPJ", unique = true, nullable = false, length = 30)
    private String CNPJ;
    @Column(name = "Nome_Fantasia", nullable = false, length = 150)
    private String nomeFantasia;
    @Column(name = "Endereco", unique = true, length = 200)
    private String Endereco;
    @Column(name = "Telefone_contato", unique = true, length = 20)
    private String telefoneContato;
    @Column(name = "Email_contato", unique = true, length = 120)
    private String emailContato;
    @OneToMany(mappedBy="Fornecedor")
    private Set<CategoriaProduto> categoriaProduto;

    public void setId_fornecedor(Long id_fornecedor) {
        this.id_fornecedor = id_fornecedor;
    }

    public Long getId_fornecedor() {
        return id_fornecedor;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    public String getEmailContato() {
        return emailContato;
    }

    public void setEmailContato(String emailContato) {
        this.emailContato = emailContato;
    }

    public Set<CategoriaProduto> getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(Set<CategoriaProduto> categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id_fornecedor +
                ", Razao social='" + razaoSocial + '\'' +
                ", CNPJ='" + CNPJ + '\'' +
                ", Nome Fanstasia='" + nomeFantasia + '\'' +
                ", Endereço='" + Endereco + '\'' +
                ", Telefone de contato='" + telefoneContato + '\'' +
                ", E-mail de contato='" + emailContato + '\'' +
                ", Categoria de produto'" + categoriaProduto + '\'' +
                '}';
    }
}


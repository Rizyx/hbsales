package br.com.hbsis.categoriaProduto;

import br.com.hbsis.fornecedor.Fornecedor;
import javax.persistence.*;

@Entity
@Table(name = "seg_categoria_produtos")
public class CategoriaProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cod_categoria", unique = true, nullable = false, length = 100)
    private String codCategoria;
    @Column(name = "nome_categoria", unique = true, nullable = false, length = 100)
    private String nomeCategoria;
    @ManyToOne
    @JoinColumn(name="fornecedor_categoria", referencedColumnName = "id")
    private Fornecedor fornecedor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(String codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", Codigo da categoria='" + codCategoria + '\'' +
                ", Nome da categoria='" + nomeCategoria + '\'' +
                ", Fornecedor da categoria='" + fornecedor + '\'' +
                '}';
    }
}

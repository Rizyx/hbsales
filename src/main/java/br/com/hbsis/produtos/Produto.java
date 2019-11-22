package br.com.hbsis.produtos;

import javax.persistence.*;

@Entity
@Table(name = "seg_produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Nome_categoria", unique = true, nullable = false, length = 100)
    private String Nome_categoria;
    @Column(name = "Fornecedor_categoria", nullable = false, length = 100)
    private String Fornecedor_categoria;

    public Long getId() {
        return id;
    }

    public String getNome_categoria() {
        return Nome_categoria;
    }

    public void setNome_categoria(String nome_categoria) {
        this.Nome_categoria = nome_categoria;
    }

    public String getFornecedor_categoria() {
        return Fornecedor_categoria;
    }

    public void setFornecedor_categoria(String fornecedor_categoria) {
        this.Fornecedor_categoria = fornecedor_categoria;
    }
    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", Nome da categoria='" + Nome_categoria + '\'' +
                ", Fornecedor da_categoria='" + Fornecedor_categoria + '\'' +
                '}';
    }
}

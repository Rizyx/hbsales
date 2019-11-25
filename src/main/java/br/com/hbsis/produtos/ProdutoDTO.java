package br.com.hbsis.produtos;

import br.com.hbsis.fornecedor.Fornecedor;

public class ProdutoDTO {
    private long id;
    private String Nome_categoria;
    private int Fornecedor_categoria;

    public ProdutoDTO() {
    }
    public ProdutoDTO(String Nome_categoria, int Fornecedor_categoria) {
        this.Nome_categoria = Nome_categoria;
        this.Fornecedor_categoria = Fornecedor_categoria;
    }
    public ProdutoDTO(Long id, String Nome_categoria, int Fornecedor_categoria) {
        this.id = id;
        this.Nome_categoria = Nome_categoria;
        this.Fornecedor_categoria = Fornecedor_categoria;
    }
    public static ProdutoDTO of(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome_categoria(),
                produto.getFornecedor_categoria()
        );
    }

    public long getId(long id) {
        return id;
    }

    public String getNome_categoria() {
        return Nome_categoria;
    }

    public void setNome_categoria(String nome_categoria) {
        this.Nome_categoria = nome_categoria;
    }

    public int getFornecedor_categoria() {
        return Fornecedor_categoria;
    }

    public void setFornecedor_categoria(int fornecedor_categoria) {
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

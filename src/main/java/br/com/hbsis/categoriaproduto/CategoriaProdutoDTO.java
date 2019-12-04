package br.com.hbsis.categoriaproduto;

public class CategoriaProdutoDTO {

    private long idCategoriaProdutos;
    private String codCategoria;
    private String nomeCategoria;
    private long idFornecedor;

    public CategoriaProdutoDTO(){
    }

    public CategoriaProdutoDTO(String codCategoria, String nomeCategoria, long idFornecedor) {
        this.codCategoria = codCategoria;
        this.nomeCategoria = nomeCategoria;
        this.idFornecedor = idFornecedor;
    }

    public CategoriaProdutoDTO(Long id, String codCategoria, String nomeCategoria, long idFornecedor) {
        this.idCategoriaProdutos = id;
        this.codCategoria = codCategoria;
        this.nomeCategoria = nomeCategoria;
        this.idFornecedor = idFornecedor;
    }

    public static CategoriaProdutoDTO of(CategoriaProduto categoriaProduto) {
        return new CategoriaProdutoDTO(
                categoriaProduto.getId(),
                categoriaProduto.getCodCategoria(),
                categoriaProduto.getNomeCategoria(),
                categoriaProduto.getFornecedor().getId()
        );
    }

    public long getIdCategoriaProdutos() {
        return idCategoriaProdutos;
    }

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

    public long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + idCategoriaProdutos +
                ", Codigo da_categoria='" + codCategoria + '\'' +
                ", Nome da categoria='" + nomeCategoria + '\'' +
                ", Id do Fornecedor da_categoria='" + idFornecedor + '\'' +
                '}';
    }
}

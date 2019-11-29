package br.com.hbsis.categoriaProduto;

public class CategoriaProdutoDTO {
    private long id_categoria_produtos;
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
    public CategoriaProdutoDTO(Long id_categoria_produtos, String codCategoria, String nomeCategoria, long idFornecedor) {
        this.id_categoria_produtos = id_categoria_produtos;
        this.codCategoria = codCategoria;
        this.nomeCategoria = nomeCategoria;
        this.idFornecedor = idFornecedor;
    }


    public static CategoriaProdutoDTO of(CategoriaProduto categoriaProduto) {
        return new CategoriaProdutoDTO(
                categoriaProduto.getId_categoria_produtos(),
                categoriaProduto.getCodCategoria(),
                categoriaProduto.getNomeCategoria(),
                categoriaProduto.getFornecedor().getId_fornecedor()
        );
    }

    public long getId_categoria_produtos() {
        return id_categoria_produtos;
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
                "id=" + id_categoria_produtos +
                ", Codigo da_categoria='" + codCategoria + '\'' +
                ", Nome da categoria='" + nomeCategoria + '\'' +
                ", Id do Fornecedor da_categoria='" + idFornecedor + '\'' +
                '}';
    }
}

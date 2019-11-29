package br.com.hbsis.linhaCategoria;



public class LinhaCategoriaDTO {
    private long id_linha_categoria;
    private String codLinhaCategoria;
    private Long idCategoriaProduto;
    private String nomeLinha;

    public LinhaCategoriaDTO(){
    }
    public LinhaCategoriaDTO(String codLinhaCategoria, Long idCategoriaProduto, String nomeLinha) {
        this.codLinhaCategoria = codLinhaCategoria;
        this.idCategoriaProduto = idCategoriaProduto;
        this.nomeLinha = nomeLinha;
    }
    public LinhaCategoriaDTO(Long id_linha_categoria, String codLinhaCategoria, Long idCategoriaProduto, String nomeLinha) {
        this.id_linha_categoria = id_linha_categoria;
        this.codLinhaCategoria = codLinhaCategoria;
        this.idCategoriaProduto = idCategoriaProduto;
        this.nomeLinha = nomeLinha;
    }
    public static LinhaCategoriaDTO of(LinhaCategoria linhaCategoria) {
        return new LinhaCategoriaDTO(
                linhaCategoria.getId_linha_categoria(),
                linhaCategoria.getCodLinhaCategoria(),
                linhaCategoria.getCategoriaProduto().getId_categoria_produtos(),
                linhaCategoria.getNomeLinha()
        );
    }

    public long getId_linha_categoria() {
        return id_linha_categoria;
    }

    public void setId_linha_categoria(long id_linha_categoria) {
        this.id_linha_categoria = id_linha_categoria;
    }

    public String getCodLinhaCategoria() {
        return codLinhaCategoria;
    }

    public void setCodLinhaCategoria(String codLinhaCategoria) {
        this.codLinhaCategoria = codLinhaCategoria;
    }

    public Long getIdCategoriaProduto() {
        return idCategoriaProduto;
    }

    public void setIdCategoriaProduto(Long idCategoriaProduto) {
        this.idCategoriaProduto = idCategoriaProduto;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }
    @Override
    public String toString() {
        return "Produto{" +
                "id_linha_categoria=" + id_linha_categoria +
                ", Código da Linha da Categoria de produtos='" + codLinhaCategoria + '\'' +
                ", Categoria da linha='" + idCategoriaProduto + '\'' +
                ", Nome da linha='" + nomeLinha + '\'' +
                '}';
    }
}

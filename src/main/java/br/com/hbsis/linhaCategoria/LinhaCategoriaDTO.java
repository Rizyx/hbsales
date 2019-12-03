package br.com.hbsis.linhaCategoria;

public class LinhaCategoriaDTO {
    private long idLinhaCategoria;
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

    public LinhaCategoriaDTO(Long id, String codLinhaCategoria, Long idCategoriaProduto, String nomeLinha) {
        this.idLinhaCategoria = id;
        this.codLinhaCategoria = codLinhaCategoria;
        this.idCategoriaProduto = idCategoriaProduto;
        this.nomeLinha = nomeLinha;
    }

    public static LinhaCategoriaDTO of(LinhaCategoria linhaCategoria) {
        return new LinhaCategoriaDTO(
                linhaCategoria.getId(),
                linhaCategoria.getCodLinhaCategoria(),
                linhaCategoria.getCategoriaProduto().getId(),
                linhaCategoria.getNomeLinha()
        );
    }

    public long getIdLinhaCategoria() { return idLinhaCategoria; }

    public void setIdLinhaCategoria(long idLinhaCategoria) { this.idLinhaCategoria = idLinhaCategoria; }

    public String getCodLinhaCategoria() { return codLinhaCategoria; }

    public void setCodLinhaCategoria(String codLinhaCategoria) { this.codLinhaCategoria = codLinhaCategoria; }

    public Long getIdCategoriaProduto() { return idCategoriaProduto; }

    public void setIdCategoriaProduto(Long idCategoriaProduto) { this.idCategoriaProduto = idCategoriaProduto; }

    public String getNomeLinha() { return nomeLinha; }

    public void setNomeLinha(String nomeLinha) { this.nomeLinha = nomeLinha; }

    @Override
    public String toString() {
        return "Produto{" +
                "id_linha_categoria=" + idLinhaCategoria +
                ", Código da Linha da Categoria de produtos='" + codLinhaCategoria + '\'' +
                ", Categoria da linha='" + idCategoriaProduto + '\'' +
                ", Nome da linha='" + nomeLinha + '\'' +
                '}';
    }
}

package br.com.hbsis.produto;

import java.util.Date;

public class ProdutoDTO {
    private long id_produto;
    private String codProduto;
    private String nomeProduto;
    private double precoProduto;
    private long LinhaCategoria;
    private long unidadeCaixaProduto;
    private double pesoUnidade;
    private Date validade;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String codProduto, String nomeProduto, double precoProduto,
                      long linhaCategoria, long unidadeCaixaProduto, double pesoUnidade, Date validade) {
        this.codProduto = codProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.LinhaCategoria = linhaCategoria;
        this.unidadeCaixaProduto = unidadeCaixaProduto;
        this.pesoUnidade = pesoUnidade;
        this.validade = validade;
    }

    public ProdutoDTO(long id_produto, String codProduto, String nomeProduto, double precoProduto,
                      long linhaCategoria, long unidadeCaixaProduto, double pesoUnidade, Date validade) {
        this.id_produto = id_produto;
        this.codProduto = codProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.LinhaCategoria = linhaCategoria;
        this.unidadeCaixaProduto = unidadeCaixaProduto;
        this.pesoUnidade = pesoUnidade;
        this.validade = validade;
    }
    public static ProdutoDTO of(Produto produto) {
        return new ProdutoDTO(
                produto.getId_produto(),
                produto.getCodProduto(),
                produto.getNomeProduto(),
                produto.getPrecoProduto(),
                produto.getLinhaCategoria().getId_linha_categoria(),
                produto.getUnidadeCaixaProduto(),
                produto.getPesoUnidade(),
                produto.getValidade()
        );
    }

    public long getId_produto() {
        return id_produto;
    }

    public void setId_produto(long id_produto) {
        this.id_produto = id_produto;
    }

    public String getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(String codProduto) {
        this.codProduto = codProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(double precoProduto) {
        this.precoProduto = precoProduto;
    }

    public long getLinhaCategoria() {
        return LinhaCategoria;
    }

    public void setLinhaCategoria(long linhaCategoria) {
        LinhaCategoria = linhaCategoria;
    }

    public long getUnidadeCaixaProduto() {
        return unidadeCaixaProduto;
    }

    public void setUnidadeCaixaProduto(long unidadeCaixaProduto) {
        this.unidadeCaixaProduto = unidadeCaixaProduto;
    }

    public double getPesoUnidade() {
        return pesoUnidade;
    }

    public void setPesoUnidade(double pesoUnidade) {
        this.pesoUnidade = pesoUnidade;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }
    @Override
    public String toString() {
        return "Produto{" +
                "id_produto=" + id_produto +
                ", Código do produto='" + codProduto + '\'' +
                ", Nome do produto='" + nomeProduto + '\'' +
                ", Preço do produto='" + precoProduto + '\'' +
                ", Linha da categoria do produto='" + LinhaCategoria + '\'' +
                ", Unidade por caixa='" + unidadeCaixaProduto + '\'' +
                ", Peso do produto='" + pesoUnidade + '\'' +
                ", Validade do produto='" + validade + '\'' +
                '}';
    }
}

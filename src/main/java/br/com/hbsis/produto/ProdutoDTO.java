package br.com.hbsis.produto;

import java.time.LocalDateTime;

public class ProdutoDTO {
    private long idProduto;
    private String codProduto;
    private String nomeProduto;
    private double precoProduto;
    private long linhaCategoria;
    private long unidadeCaixaProduto;
    private double pesoUnidade;
    private LocalDateTime validade;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String codProduto, String nomeProduto, double precoProduto,
                      long linhaCategoria, long unidadeCaixaProduto, double pesoUnidade, LocalDateTime validade) {
        this.codProduto = codProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.linhaCategoria = linhaCategoria;
        this.unidadeCaixaProduto = unidadeCaixaProduto;
        this.pesoUnidade = pesoUnidade;
        this.validade = validade;
    }

    public ProdutoDTO(long id, String codProduto, String nomeProduto, double precoProduto,
                      long linhaCategoria, long unidadeCaixaProduto, double pesoUnidade, LocalDateTime validade) {
        this.idProduto = id;
        this.codProduto = codProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.linhaCategoria = linhaCategoria;
        this.unidadeCaixaProduto = unidadeCaixaProduto;
        this.pesoUnidade = pesoUnidade;
        this.validade = validade;
    }

    public static ProdutoDTO of(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getCodProduto(),
                produto.getNomeProduto(),
                produto.getPrecoProduto(),
                produto.getLinhaCategoria().getId(),
                produto.getUnidadeCaixaProduto(),
                produto.getPesoUnidade(),
                produto.getValidade()
        );
    }

    public long getIdProduto() { return idProduto; }

    public void setIdProduto(long idProduto) { this.idProduto = idProduto; }

    public String getCodProduto() { return codProduto; }

    public void setCodProduto(String codProduto) { this.codProduto = codProduto; }

    public String getNomeProduto() { return nomeProduto; }

    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }

    public double getPrecoProduto() { return precoProduto; }

    public void setPrecoProduto(double precoProduto) { this.precoProduto = precoProduto; }

    public long getLinhaCategoria() { return linhaCategoria; }

    public void setLinhaCategoria(long linhaCategoria) { this.linhaCategoria = linhaCategoria; }

    public long getUnidadeCaixaProduto() { return unidadeCaixaProduto; }

    public void setUnidadeCaixaProduto(long unidadeCaixaProduto) { this.unidadeCaixaProduto = unidadeCaixaProduto; }

    public double getPesoUnidade() { return pesoUnidade; }

    public void setPesoUnidade(double pesoUnidade) { this.pesoUnidade = pesoUnidade; }

    public LocalDateTime getValidade() { return validade; }

    public void setValidade(LocalDateTime validade) { this.validade = validade; }

    @Override
    public String toString() {
        return "Produto{" +
                "id_produto=" + idProduto +
                ", Código do produto='" + codProduto + '\'' +
                ", Nome do produto='" + nomeProduto + '\'' +
                ", Preço do produto='" + precoProduto + '\'' +
                ", Linha da categoria do produto='" + linhaCategoria + '\'' +
                ", Unidade por caixa='" + unidadeCaixaProduto + '\'' +
                ", Peso do produto='" + pesoUnidade + '\'' +
                ", Validade do produto='" + validade + '\'' +
                '}';
    }
}

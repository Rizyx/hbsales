package br.com.hbsis.produto;

import br.com.hbsis.linhaCategoria.LinhaCategoria;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cod_produto", unique = true, nullable = false, length = 100)
    private String codProduto;
    @Column(name = "nome_produto", unique = true, nullable = false, length = 100)
    private String nomeProduto;
    @Column(name = "preco_produto", nullable = false)
    private double precoProduto;
    @ManyToOne
    @JoinColumn(name = "linha_produto", referencedColumnName ="id")
    private LinhaCategoria LinhaCategoria;
    @Column(name = "unidade_caixa_produto")
    private long unidadeCaixaProduto;
    @Column(name = "peso_unidade")
    private double pesoUnidade;
    @Column(name = "validade")
    private LocalDateTime validade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getPrecoProduto() { return precoProduto; }

    public void setPrecoProduto(double precoProduto) {
        this.precoProduto = precoProduto;
    }

    public LinhaCategoria getLinhaCategoria() {
        return LinhaCategoria;
    }

    public void setLinhaCategoria(LinhaCategoria linhaCategoria) { LinhaCategoria = linhaCategoria;}

    public long getUnidadeCaixaProduto() { return unidadeCaixaProduto; }

    public void setUnidadeCaixaProduto(long unidadeCaixaProduto) {
        this.unidadeCaixaProduto = unidadeCaixaProduto;
    }

    public double getPesoUnidade() {
        return pesoUnidade;
    }

    public void setPesoUnidade(double pesoUnidade) {
        this.pesoUnidade = pesoUnidade;
    }

    public LocalDateTime getValidade() {
        return validade;
    }

    public void setValidade(LocalDateTime validade) {
        this.validade = validade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id_produto=" + id +
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

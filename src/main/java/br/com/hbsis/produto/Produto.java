package br.com.hbsis.produto;

import br.com.hbsis.linhaCategoria.LinhaCategoria;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "seg_produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produto;
    @Column(name = "cod_produto", unique = true, nullable = false, length = 100)
    private String codProduto;
    @Column(name = "nome_produto", unique = true, nullable = false, length = 100)
    private String nomeProduto;
    @Column(name = "preco_produto", nullable = false)
    private double precoProduto;
    @ManyToOne
    @JoinColumn(name = "linha_produto", referencedColumnName ="id_linha_categoria")
    private LinhaCategoria LinhaCategoria;
    @Column(name = "unidade_caixa_produto")
    private long unidadeCaixaProduto;
    @Column(name = "peso_unidade")
    private double pesoUnidade;
    @Column(name = "validade")
    private Date validade;
    @Transient
    private MultipartFile file;

    public Long getId_produto() {
        return id_produto;
    }

    public void setId_produto(Long id_produto) {
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

    public br.com.hbsis.linhaCategoria.LinhaCategoria getLinhaCategoria() {
        return LinhaCategoria;
    }

    public void setLinhaCategoria(br.com.hbsis.linhaCategoria.LinhaCategoria linhaCategoria) {
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
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

package br.com.hbsis.pedido;

import br.com.hbsis.produto.Produto;
import br.com.hbsis.vendafornecedor.VendaFornecedor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_funcionario", nullable = false, length = 100)
    private String nomeFuncionario;
    @ManyToOne
    @JoinColumn(name = "produto", referencedColumnName ="id")
    private Produto produto;
    @Column(name = "quantidade_produto", nullable = false)
    private int quantidadeProduto;
    @Column(name = "status_pedido", nullable = false)
    private String statusPedido;
    @ManyToOne
    @JoinColumn(name = "periodo_vendas", referencedColumnName ="id")
    private VendaFornecedor vendaFornecedor;
    @Column(name = "data_retirada")
    private LocalDateTime dataRetirada;


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNomeFuncionario() { return nomeFuncionario; }

    public void setNomeFuncionario(String nomeFuncionario) { this.nomeFuncionario = nomeFuncionario; }

    public Produto getProduto() { return produto; }

    public void setProduto(Produto produto) { this.produto = produto; }

    public int getQuantidadeProduto() { return quantidadeProduto; }

    public void setQuantidadeProduto(int quantidadeProduto) { this.quantidadeProduto = quantidadeProduto; }

    public String getStatusPedido() { return statusPedido; }

    public void setStatusPedido(String statusPedido) { this.statusPedido = statusPedido; }

    public VendaFornecedor getVendaFornecedor() { return vendaFornecedor; }

    public void setVendaFornecedor(VendaFornecedor vendaFornecedor) { this.vendaFornecedor = vendaFornecedor; }

    public LocalDateTime getDataRetirada() { return dataRetirada; }

    public void setDataRetirada(LocalDateTime dataRetirada) { this.dataRetirada = dataRetirada; }

    @Override
    public String toString() {
        return "Pedido{" +
                "id do pedido=" + id +
                ", Nome do funcionario='" + nomeFuncionario + '\'' +
                ", Produtos='" + produto + '\'' +
                ", Quantidade de produtos='" + quantidadeProduto + '\'' +
                ", Status do pedido='" + statusPedido + '\'' +
                ", Periodo de vendas='" + vendaFornecedor + '\'' +
                ", Data de retirada ='" + dataRetirada + '\'' +
                '}';
    }
}

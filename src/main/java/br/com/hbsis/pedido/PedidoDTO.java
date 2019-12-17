package br.com.hbsis.pedido;

import java.time.LocalDateTime;

public class PedidoDTO {
    private long idPedido;
    private String nomeFuncionario;
    private long produto;
    private int quantidadeProduto;
    private String statusPedido;
    private long vendaFornecedor;
    private LocalDateTime dataRetirada;

    public PedidoDTO() {
    }

    public PedidoDTO(String nomeFuncionario, long produto, int quantidadeProduto,
                     String statusPedido,long vendaFornecedor, LocalDateTime dataRetirada) {
        this.nomeFuncionario = nomeFuncionario;
        this.produto = produto;
        this.quantidadeProduto = quantidadeProduto;
        this.statusPedido = statusPedido;
        this.vendaFornecedor = vendaFornecedor;
        this.dataRetirada = dataRetirada;
    }

    public PedidoDTO(long idPedido, String nomeFuncionario, long produto,
                     int quantidadeProduto,String statusPedido,long vendaFornecedor, LocalDateTime dataRetirada) {
        this.idPedido = idPedido;
        this.nomeFuncionario = nomeFuncionario;
        this.produto = produto;
        this.quantidadeProduto = quantidadeProduto;
        this.statusPedido = statusPedido;
        this.vendaFornecedor = vendaFornecedor;
        this.dataRetirada = dataRetirada;
    }

    public static PedidoDTO of(Pedido pedido) {
        return new PedidoDTO(
                pedido.getId(),
                pedido.getNomeFuncionario(),
                pedido.getProduto().getId(),
                pedido.getQuantidadeProduto(),
                pedido.getStatusPedido(),
                pedido.getVendaFornecedor().getId(),
                pedido.getDataRetirada()
        );
    }

    public long getIdPedido() { return idPedido; }

    public void setIdPedido(long idPedido) { this.idPedido = idPedido; }

    public String getNomeFuncionario() { return nomeFuncionario; }

    public void setNomeFuncionario(String nomeFuncionario) { this.nomeFuncionario = nomeFuncionario; }

    public long getProduto() { return produto; }

    public void setProduto(long produto) { this.produto = produto; }

    public int getQuantidadeProduto() { return quantidadeProduto; }

    public void setQuantidadeProduto(int quantidadeProduto) { this.quantidadeProduto = quantidadeProduto; }

    public String getStatusPedido() { return statusPedido; }

    public void setStatusPedido(String statusPedido) { this.statusPedido = statusPedido; }

    public long getVendaFornecedor() { return vendaFornecedor; }

    public void setVendaFornecedor(long vendaFornecedor) { this.vendaFornecedor = vendaFornecedor; }

    public LocalDateTime getDataRetirada() { return dataRetirada; }

    public void setDataRetirada(LocalDateTime dataRetirada) { this.dataRetirada = dataRetirada; }

    @Override
    public String toString() {
        return "Pedido{" +
                "id do pedido=" + idPedido +
                ", Nome do funcionario='" + nomeFuncionario + '\'' +
                ", Produtos='" + produto + '\'' +
                ", Quantidade de produtos='" + quantidadeProduto + '\'' +
                ", Status do pedido='" + statusPedido + '\'' +
                ", Periodo de vendas='" + vendaFornecedor + '\'' +
                ", Data de retirada='" + dataRetirada + '\'' +
                '}';
    }
}

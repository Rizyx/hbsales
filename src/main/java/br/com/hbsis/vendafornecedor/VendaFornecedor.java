package br.com.hbsis.vendafornecedor;

import br.com.hbsis.fornecedor.Fornecedor;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "seg_venda_fornecedor")
public class VendaFornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;
    @Column(name = "data_final")
    private LocalDateTime dataFinal;
    @Column(name = "dia_retirada")
    private LocalDateTime diaRetirada;
    @ManyToOne
    @JoinColumn(name="venda_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;
    @Column(name = "descricao")
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }

    public LocalDateTime getDiaRetirada() {
        return diaRetirada;
    }

    public void setDiaRetirada(LocalDateTime diaRetirada) {
        this.diaRetirada = diaRetirada;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() {
        return "VendaFornecedor{" +
                "id=" + id +
                ", Data de inicio='" + dataInicio + '\'' +
                ", Data de final='" + dataFinal + '\'' +
                ", Dia de retirada='" + diaRetirada + '\'' +
                ", id vendaFornecedor='" + fornecedor + '\'' +
                ", descrição do produto='" + descricao + '\'' +
                '}';
    }
}

package br.com.hbsis.vendafornecedor;

import java.time.LocalDateTime;

public class VendaFornecedorDTO {

    private long id;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFinal;
    private LocalDateTime diaRetirada;
    private long idFornecedor;

    public VendaFornecedorDTO() {
    }

    public VendaFornecedorDTO(LocalDateTime dataInicio, LocalDateTime dataFinal,
                                    LocalDateTime diaRetirada, long idFornecedor) {
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.diaRetirada = diaRetirada;
        this.idFornecedor = idFornecedor;
    }

    public VendaFornecedorDTO(long id, LocalDateTime dataInicio,
                              LocalDateTime dataFinal, LocalDateTime diaRetirada, long idFornecedor) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.diaRetirada = diaRetirada;
        this.idFornecedor = idFornecedor;
    }
    public static VendaFornecedorDTO of(VendaFornecedor vendaFornecedor) {
        return new VendaFornecedorDTO(
                vendaFornecedor.getId(),
                vendaFornecedor.getDataInicio(),
                vendaFornecedor.getDataFinal(),
                vendaFornecedor.getDiaRetirada(),
                vendaFornecedor.getFornecedor().getId()
        );
    }

    public long getId() { return id;}

    public void setId(long id) { this.id = id;}

    public LocalDateTime getDataInicio() { return dataInicio;}

    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio;}

    public LocalDateTime getDataFinal() { return dataFinal; }

    public void setDataFinal(LocalDateTime dataFinal) { this.dataFinal = dataFinal;}

    public LocalDateTime getDiaRetirada() { return diaRetirada; }

    public void setDiaRetirada(LocalDateTime diaRetirada) { this.diaRetirada = diaRetirada;}

    public long getIdFornecedor() { return idFornecedor; }

    public void setIdFornecedor(long idFornecedor) { this.idFornecedor = idFornecedor;}

    @Override
    public String toString() {
        return "VendaFornecedor{" +
                "id=" + id +
                ", Data de inicio='" + dataInicio + '\'' +
                ", Data de final='" + dataFinal + '\'' +
                ", Dia de retirada='" + diaRetirada + '\'' +
                ", id vendaFornecedor='" + idFornecedor + '\'' +
                '}';
    }
}

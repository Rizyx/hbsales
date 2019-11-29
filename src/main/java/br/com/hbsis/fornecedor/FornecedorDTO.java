package br.com.hbsis.fornecedor;

public class FornecedorDTO {
    private long idFornecedor;
    private String razaoSocial;
    private String CNPJ;
    private String nomeFantasia;
    private String Endereco;
    private String telefoneContato;
    private String emailContato;

    public FornecedorDTO() {
    }
    public FornecedorDTO(String razaoSocial, String CNPJ) {
        this.razaoSocial = razaoSocial;
        this.CNPJ = CNPJ;
    }
    public FornecedorDTO(Long id_fornecedor, String razaoSocial, String CNPJ, String nomeFantasia, String Endereco,
                         String telefoneContato, String emailContato) {
        this.idFornecedor = id_fornecedor;
        this.razaoSocial = razaoSocial;
        this.CNPJ = CNPJ;
        this.nomeFantasia = nomeFantasia;
        this.Endereco = Endereco;
        this.telefoneContato = telefoneContato;
        this.emailContato = emailContato;


    }

    public static FornecedorDTO of(Fornecedor fornecedor) {
        return new FornecedorDTO(
                fornecedor.getId_fornecedor(),
                fornecedor.getRazaoSocial(),
                fornecedor.getCNPJ(),
                fornecedor.getNomeFantasia(),
                fornecedor.getEndereco(),
                fornecedor.getTelefoneContato(),
                fornecedor.getEmailContato()
        );
    }

    public void setIdFornecedor(long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public long getIdFornecedor() {
        return idFornecedor;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        this.Endereco = endereco;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    public String getEmailContato() {
        return emailContato;
    }

    public void setEmailContato(String emailContato) {
        this.emailContato = emailContato;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + idFornecedor +
                ", Razao social='" + razaoSocial + '\'' +
                ", CNPJ='" + CNPJ + '\'' +
                ", Nome Fanstasia='" + nomeFantasia + '\'' +
                ", Endereço='" + Endereco + '\'' +
                ", Telefone de contato='" + telefoneContato + '\'' +
                ", E-mail de contato='" + emailContato + '\'' +
                '}';
    }

}


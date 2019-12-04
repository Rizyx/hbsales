package br.com.hbsis.fornecedor;

public class FornecedorDTO {
    private long idFornecedor;
    private String razaoSocial;
    private String cnpj;
    private String nomeFantasia;
    private String endereco;
    private String telefoneContato;
    private String emailContato;

    public FornecedorDTO() {
    }

    public FornecedorDTO(Long id, String razaoSocial, String cnpj, String nomeFantasia, String Endereco,
                         String telefoneContato, String emailContato) {
        this.idFornecedor = id;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
        this.endereco = Endereco;
        this.telefoneContato = telefoneContato;
        this.emailContato = emailContato;
    }

    public FornecedorDTO(long idFornecedor ,String razaoSocial, String cnpj, String nomeFantasia) {
        this.idFornecedor = idFornecedor;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
    }

    public static FornecedorDTO of(Fornecedor fornecedor) {
        return new FornecedorDTO(
                fornecedor.getId(),
                fornecedor.getRazaoSocial(),
                fornecedor.getCnpj(),
                fornecedor.getNomeFantasia(),
                fornecedor.getEndereco(),
                fornecedor.getTelefoneContato(),
                fornecedor.getEmailContato()
        );
    }

    public void setIdFornecedor(long idFornecedor) { this.idFornecedor = idFornecedor; }

    public long getIdFornecedor() {
        return idFornecedor;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
                ", CNPJ='" + cnpj + '\'' +
                ", Nome Fanstasia='" + nomeFantasia + '\'' +
                ", Endereço='" + endereco + '\'' +
                ", Telefone de contato='" + telefoneContato + '\'' +
                ", E-mail de contato='" + emailContato + '\'' +
                '}';
    }
}


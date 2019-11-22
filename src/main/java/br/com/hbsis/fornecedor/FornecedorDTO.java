package br.com.hbsis.fornecedor;

public class FornecedorDTO {
    private long id;
    private String Razao_social;
    private String CNPJ;
    private String Nome_fantasia;
    private String Endereco;
    private String Telefone_contato;
    private String Email_contato;

    public FornecedorDTO() {
    }
    public FornecedorDTO(String Razao_social, String CNPJ) {
        this.Razao_social = Razao_social;
        this.CNPJ = CNPJ;
    }
    public FornecedorDTO(Long id, String Razao_social, String CNPJ, String Nome_fantasia, String Endereco,
                            String Telefone_contato, String Email_contato) {
        this.id = id;
        this.Razao_social = Razao_social;
        this.CNPJ = CNPJ;
        this.Nome_fantasia = Nome_fantasia;
        this.Endereco = Endereco;
        this.Email_contato = Email_contato;

    }

    public static FornecedorDTO of(Fornecedor fornecedor) {
        return new FornecedorDTO(
                fornecedor.getId(),
                fornecedor.getRazao_Social(),
                fornecedor.getCNPJ(),
                fornecedor.getNome_Fantasia(),
                fornecedor.getEndereco(),
                fornecedor.getTelefone_contato(),
                fornecedor.getEmail_contato()
        );
    }

    public long getId(long id) {
        return id;
    }


    public String getRazao_social() {
        return Razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.Razao_social = razao_social;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getNome_fantasia() {
        return Nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.Nome_fantasia = nome_fantasia;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        this.Endereco = endereco;
    }

    public String getTelefone_contato() {
        return Telefone_contato;
    }

    public void setTelefone_contato(String telefone_contato) {
        this.Telefone_contato = telefone_contato;
    }

    public String getEmail_contato() {
        return Email_contato;
    }

    public void setEmail_contato(String email_contato) {
        this.Email_contato = email_contato;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id +
                ", Razao social='" + Razao_social + '\'' +
                ", CNPJ='" + CNPJ + '\'' +
                ", Nome Fanstasia='" + Nome_fantasia + '\'' +
                ", Endereço='" + Endereco + '\'' +
                ", Telefone de contato='" + Telefone_contato + '\'' +
                ", E-mail de contato='" + Email_contato + '\'' +
                '}';
    }

}


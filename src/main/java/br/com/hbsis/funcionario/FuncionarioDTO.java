package br.com.hbsis.funcionario;

public class FuncionarioDTO {
    private long idFuncionario;
    private String nomeFuncionario;
    private String emailFuncionario;
    private String nome;

    public FuncionarioDTO() {
    }

    public FuncionarioDTO(String nomeFuncionario, String emailFuncionario,String nome) {
        this.nomeFuncionario = nomeFuncionario;
        this.emailFuncionario = emailFuncionario;
        this.nome = nome;
    }

    public FuncionarioDTO(long idFuncionario, String nomeFuncionario, String emailFuncionario,String nome) {
        this.idFuncionario = idFuncionario;
        this.nomeFuncionario = nomeFuncionario;
        this.emailFuncionario = emailFuncionario;
        this.nome = nome;
    }

    public static FuncionarioDTO of(Funcionario funcionario) {
        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNomeFuncionario(),
                funcionario.getEmailFuncionario(),
                funcionario.getNome()
        );
    }

    public long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNomeFuncionario() { return nomeFuncionario; }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getEmailFuncionario() {
        return emailFuncionario;
    }

    public void setEmailFuncionario(String emailFuncionario) {
        this.emailFuncionario = emailFuncionario;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + idFuncionario +
                ", Nome do funcionario='" + nomeFuncionario + '\'' +
                ", Email do funcionario='" + emailFuncionario + '\''+
                ", Nome do funcionario='" + nome + '\''+
                '}';
    }
}

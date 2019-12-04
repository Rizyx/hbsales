package br.com.hbsis.funcionario;

public class FuncionarioDTO {
    private long idFuncionario;
    private String nomeFuncionario;
    private String emailFuncionario;

    public FuncionarioDTO() {
    }

    public FuncionarioDTO(String nomeFuncionario, String emailFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
        this.emailFuncionario = emailFuncionario;
    }

    public FuncionarioDTO(long idFuncionario, String nomeFuncionario, String emailFuncionario) {
        this.idFuncionario = idFuncionario;
        this.nomeFuncionario = nomeFuncionario;
        this.emailFuncionario = emailFuncionario;
    }

    public static FuncionarioDTO of(Funcionario funcionario) {
        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNomeFuncionario(),
                funcionario.getEmailFuncionario()
        );
    }

    public long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getEmailFuncionario() {
        return emailFuncionario;
    }

    public void setEmailFuncionario(String emailFuncionario) {
        this.emailFuncionario = emailFuncionario;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + idFuncionario +
                ", Nome do funcionario='" + nomeFuncionario + '\'' +
                ", Email do funcionario='" + emailFuncionario + '\''+
                '}';
    }
}

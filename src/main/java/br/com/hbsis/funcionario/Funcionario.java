package br.com.hbsis.funcionario;

import javax.persistence.*;

@Entity
@Table(name = "seg_funcionarios")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_funcionario", nullable = false)
    private String nomeFuncionario;
    @Column(name = "email_funcionario", unique = true)
    private String emailFuncionario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", Nome do funcionario='" + nomeFuncionario + '\'' +
                ", Email do funcionario='" + emailFuncionario + '\''+
                '}';
    }
}

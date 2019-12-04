package br.com.hbsis.linhaCategoria;
import br.com.hbsis.categoriaProduto.CategoriaProduto;
import br.com.hbsis.produto.Produto;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "seg_linha_categorias")
public class LinhaCategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cod_linha_categoria", unique = true, nullable = false, length = 100)
    private String codLinhaCategoria;
    @ManyToOne
    @JoinColumn(name = "categoria_linha",  referencedColumnName = "id")
    private CategoriaProduto CategoriaProduto;
    @Column(name = "nome_linha", nullable = false, length = 100)
    private String nomeLinha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodLinhaCategoria() {
        return codLinhaCategoria;
    }

    public void setCodLinhaCategoria(String codLinhaCategoria) {
        this.codLinhaCategoria = codLinhaCategoria;
    }

    public CategoriaProduto getCategoriaProduto() {
        return CategoriaProduto;
    }

    public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
        CategoriaProduto = categoriaProduto;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id_linha_categoria=" + id +
                ", Código da Linha da Categoria de produtos='" + codLinhaCategoria + '\'' +
                ", Categoria da linha='" + CategoriaProduto + '\'' +
                ", Nome da linha='" + nomeLinha + '\'' +
                '}';
    }
}

package br.com.hbsis.linhaCategoria;
import br.com.hbsis.categoriaProduto.CategoriaProduto;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;

@Entity
@Table(name = "seg_linha_categorias")
public class LinhaCategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_linha_categoria ;
    @Column(name = "cod_linha_categoria", unique = true, nullable = false, length = 100)
    private String codLinhaCategoria;
    @ManyToOne
    @JoinColumn(name = "categoria_linha",  referencedColumnName = "id_categoria_produtos")
    private CategoriaProduto CategoriaProduto;
    @Column(name = "nome_linha", nullable = false, length = 100)
    private String nomeLinha;
    @Transient
    private MultipartFile file;

    public Long getId_linha_categoria() {
        return id_linha_categoria;
    }

    public void setId_linha_categoria(Long id_linha_categoria) {
        this.id_linha_categoria = id_linha_categoria;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id_linha_categoria=" + id_linha_categoria +
                ", Código da Linha da Categoria de produtos='" + codLinhaCategoria + '\'' +
                ", Categoria da linha='" + CategoriaProduto + '\'' +
                ", Nome da linha='" + nomeLinha + '\'' +
                '}';
    }
}

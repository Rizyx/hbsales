package br.com.hbsis.linhaCategoria;

import br.com.hbsis.categoriaProduto.CategoriaProdutoRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/linhaCategoriaProdutos")
public class LinhaCategoriaRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaProdutoRest.class);
    private final LinhaCategoriaService linhaCategoriaService;

    @Autowired
    public LinhaCategoriaRest(LinhaCategoriaService linhaCategoriaService) {
        this.linhaCategoriaService = linhaCategoriaService;
    }

    @PostMapping
    public LinhaCategoriaDTO save(@RequestBody LinhaCategoriaDTO linhaCategoriaDTO) {
        LOGGER.info("Recebendo solicitação de persistência de Produto...");
        LOGGER.debug("Payaload: {}", linhaCategoriaDTO);

        return this.linhaCategoriaService.save(linhaCategoriaDTO);
    }

    @PostMapping("/import_csv")
    public void importCsv(@RequestParam("file") MultipartFile file) throws Exception {
        linhaCategoriaService.importCSV(file);

    }

    @GetMapping("/export_csv")
    public void exportCSV(HttpServletResponse response) throws Exception {
       linhaCategoriaService.exportCSV(response);
    }

    @GetMapping("/{id}")
    public LinhaCategoriaDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by ID... id: [{}]", id);
        return this.linhaCategoriaService.findById(id);
    }

    @GetMapping("/todos")
    public List<LinhaCategoria> findAll() {
        List<LinhaCategoria> linhaCategorias = linhaCategoriaService.findAll();
        return linhaCategorias;
    }

    @PutMapping("/{id}")
    public LinhaCategoriaDTO udpate(@PathVariable("id") Long id, @RequestBody LinhaCategoriaDTO linhaCategoriaDTO) {
        LOGGER.info("Recebendo Update para Produto de ID: {}", id);
        LOGGER.debug("Payload: {}", linhaCategoriaDTO);
        return this.linhaCategoriaService.update(linhaCategoriaDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para Produto de ID: {}", id);
        this.linhaCategoriaService.delete(id);
    }
}

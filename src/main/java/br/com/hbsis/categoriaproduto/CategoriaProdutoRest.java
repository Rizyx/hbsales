package br.com.hbsis.categoriaproduto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/categoriaProdutos")
public class CategoriaProdutoRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaProdutoRest.class);
    private final CategoriaProdutoService categoriaProdutoService;

    @Autowired
    public CategoriaProdutoRest(CategoriaProdutoService categoriaProdutoService) {
        this.categoriaProdutoService = categoriaProdutoService;
    }

    @PostMapping
    public CategoriaProdutoDTO save(@RequestBody CategoriaProdutoDTO categoriaProdutoDTO) {
        LOGGER.info("Recebendo solicitação de persistência de Produto...");
        LOGGER.debug("Payaload: {}", categoriaProdutoDTO);
        return this.categoriaProdutoService.save(categoriaProdutoDTO);
    }

    @PostMapping("/import_csv")
    public void importCsv(@RequestParam("file") MultipartFile file) throws Exception {
        categoriaProdutoService.importCSV(file);
    }


    @GetMapping("/export_csv")
    public void exportCSV(HttpServletResponse response) throws Exception {
        categoriaProdutoService.exportCSV(response);
    }

    @GetMapping("/todos")
    public List<CategoriaProduto> findCategoriaProduto() {
        return categoriaProdutoService.findAll();
    }

    @GetMapping("/{id}")
    public CategoriaProdutoDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by ID... id: [{}]", id);
        return this.categoriaProdutoService.findById(id);
    }


    @PutMapping("/{id}")
    public CategoriaProdutoDTO udpate(@PathVariable("id") Long id, @RequestBody CategoriaProdutoDTO categoriaProdutoDTO) {
        LOGGER.info("Recebendo Update para Produto de ID: {}", id);
        LOGGER.debug("Payload: {}", categoriaProdutoDTO);
        return this.categoriaProdutoService.update(categoriaProdutoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para Categoria do Produto de ID: {}", id);
        this.categoriaProdutoService.delete(id);
    }
}

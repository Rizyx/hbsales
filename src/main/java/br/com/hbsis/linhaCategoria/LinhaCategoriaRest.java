package br.com.hbsis.linhaCategoria;

import br.com.hbsis.categoriaProduto.CategoriaProdutoRest;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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
        String filename = "cadastroProduto1.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        PrintWriter writer = response.getWriter();
        ICSVWriter Writer = new CSVWriterBuilder(writer)
                .withSeparator(',')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();
        for (LinhaCategoria linhaCategoria : linhaCategoriaService.findAll()) {
            Writer.writeNext(new String[]
                    {linhaCategoria.getCodLinhaCategoria(),
                            linhaCategoria.getCategoriaProduto().getId_categoria_produtos().toString(),linhaCategoria.getNomeLinha()});
        }
    }
    @GetMapping("/{id_linha_categoria}")
    public LinhaCategoriaDTO find(@PathVariable("id_linha_categoria") Long id_linha_categoria) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id_linha_categoria);

        return this.linhaCategoriaService.findById(id_linha_categoria);
    }
    @PutMapping("/{id_linha_categoria}")
    public LinhaCategoriaDTO udpate(@PathVariable("id_linha_categoria") Long id_linha_categoria, @RequestBody LinhaCategoriaDTO linhaCategoriaDTO) {
        LOGGER.info("Recebendo Update para Produto de ID: {}", id_linha_categoria);
        LOGGER.debug("Payload: {}", linhaCategoriaDTO);

        return this.linhaCategoriaService.update(linhaCategoriaDTO, id_linha_categoria);
    }
    @DeleteMapping("/{id_linha_categoria}")
    public void delete(@PathVariable("id_linha_categoria") Long id_linha_categoria) {
        LOGGER.info("Recebendo Delete para Produto de ID: {}", id_linha_categoria);

        this.linhaCategoriaService.delete(id_linha_categoria);
    }
}

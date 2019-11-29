package br.com.hbsis.categoriaProduto;

import com.opencsv.CSVWriter;

import java.io.*;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

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
        for (CategoriaProduto categoriaProduto : categoriaProdutoService.findAll()) {
           Writer.writeNext(new String[]
                   {categoriaProduto.getCodCategoria(),
                           categoriaProduto.getNomeCategoria(),categoriaProduto.getFornecedor().getId_fornecedor().toString()});
        }
    }

    @GetMapping("/{id_categoria_produtos}")
    public CategoriaProdutoDTO find(@PathVariable("id_categoria_produtos") Long id_categoria_produtos) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id_categoria_produtos);

        return this.categoriaProdutoService.findById(id_categoria_produtos);
    }


    @PutMapping("/{id_categoria_produtos}")
    public CategoriaProdutoDTO udpate(@PathVariable("id_categoria_produtos") Long id_categoria_produtos, @RequestBody CategoriaProdutoDTO categoriaProdutoDTO) {
        LOGGER.info("Recebendo Update para Produto de ID: {}", id_categoria_produtos);
        LOGGER.debug("Payload: {}", categoriaProdutoDTO);

        return this.categoriaProdutoService.update(categoriaProdutoDTO, id_categoria_produtos);
    }

    @DeleteMapping("/{id_categoria_produtos}")
    public void delete(@PathVariable("id_categoria_produtos") Long id_categoria_produtos) {
        LOGGER.info("Recebendo Delete para Produto de ID: {}", id_categoria_produtos);

        this.categoriaProdutoService.delete(id_categoria_produtos);
    }
}

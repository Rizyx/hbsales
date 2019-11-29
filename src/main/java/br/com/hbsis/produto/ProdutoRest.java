package br.com.hbsis.produto;

import br.com.hbsis.linhaCategoria.LinhaCategoriaRest;
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
@RequestMapping("/produtos")
public class ProdutoRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaCategoriaRest.class);
    private final ProdutoService produtoService;

    @Autowired
    public ProdutoRest(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }
    @PostMapping
    public ProdutoDTO save(@RequestBody ProdutoDTO produtoDTO) {
        LOGGER.info("Recebendo solicitação de persistência de Produto...");
        LOGGER.debug("Payaload: {}", produtoDTO);

        return this.produtoService.save(produtoDTO);
    }
    @PostMapping("/import_csv")
    public void importCsv(@RequestParam("file") MultipartFile file) throws Exception {
        produtoService.importCSV(file);
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
        for (Produto produto : produtoService.findAll()) {
            Writer.writeNext(new String[]
                    {produto.getCodProduto(),produto.getNomeProduto(), String.valueOf(produto.getPrecoProduto()),
                            produto.getLinhaCategoria().getId_linha_categoria().toString(),
                            String.valueOf(produto.getUnidadeCaixaProduto()),
                            String.valueOf(produto.getPesoUnidade()), String.valueOf(produto.getValidade())});
        }
    }
    @GetMapping("/{id_produto}")
    public ProdutoDTO find(@PathVariable("id_produto") Long id_produto) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id_produto);

        return this.produtoService.findById(id_produto);
    }
    @PutMapping("/{id_produto}")
    public ProdutoDTO udpate(@PathVariable("id_produto") Long id_produto, @RequestBody ProdutoDTO produtoDTO) {
        LOGGER.info("Recebendo Update para Produto de ID: {}", id_produto);
        LOGGER.debug("Payload: {}", produtoDTO);

        return this.produtoService.update(produtoDTO, id_produto);
    }
    @DeleteMapping("/{id_produto}")
    public void delete(@PathVariable("id_produto") Long id_produto) {
        LOGGER.info("Recebendo Delete para Produto de ID: {}", id_produto);

        this.produtoService.delete(id_produto);
    }
}

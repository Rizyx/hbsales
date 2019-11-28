package br.com.hbsis.categoriaProduto;

import com.opencsv.CSVWriter;

import java.io.*;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
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

        //set file name and content type
        String filename = "cadastroProduto1.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        PrintWriter writer = response.getWriter();

        ICSVWriter Writer = new CSVWriterBuilder(writer)
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();


        //write all users to csv file
        for (CategoriaProduto categoriaProduto : categoriaProdutoService.findAll()) {
           Writer.writeNext(new String[]
                   {categoriaProduto.getId().toString(),categoriaProduto.getCodCategoria(),
                           categoriaProduto.getNomeCategoria(),categoriaProduto.getFornecedor().getId_fornecedor().toString()});
        }

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
        LOGGER.info("Recebendo Delete para Produto de ID: {}", id);

        this.categoriaProdutoService.delete(id);
    }
}

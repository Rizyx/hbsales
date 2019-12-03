package br.com.hbsis.categoriaProduto;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import com.opencsv.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

@Service
public class CategoriaProdutoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaProdutoService.class);
    private final ICategoriaProdutoRepository iCategoriaProdutoRepository;
    private final FornecedorService fornecedorService;

    public CategoriaProdutoService(ICategoriaProdutoRepository iCategoriaProdutoRepository, FornecedorService fornecedorService) {
        this.iCategoriaProdutoRepository = iCategoriaProdutoRepository;
        this.fornecedorService = fornecedorService;
    }

    public  List<CategoriaProduto> findAll() {
        List<CategoriaProduto> categoriaProdutos = iCategoriaProdutoRepository.findAll();
        return categoriaProdutos;
    }

    public  void exportCSV(HttpServletResponse response) throws IOException {
        String filename = "ArquivoDeExpotação.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        PrintWriter writer = response.getWriter();
        ICSVWriter Writer = new CSVWriterBuilder(writer)
                .withSeparator(',')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();
        for (CategoriaProduto categoriaProduto : iCategoriaProdutoRepository.findAll()) {
            Writer.writeNext(new String[]
                    {categoriaProduto.getCodCategoria(),
                            categoriaProduto.getNomeCategoria(),categoriaProduto.getFornecedor().getId().toString()});
        }
    }

    public List<CategoriaProduto> importCSV(MultipartFile file) throws Exception {
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).build();
            List<String[]> rows = csvReader.readAll();
            List<CategoriaProduto> categoriaProdutos = new ArrayList<>();
            for (String[] row : rows) {
                try {
                    CategoriaProduto categoriaProduto = new CategoriaProduto();
                    Fornecedor fornecedor = new Fornecedor();
                    FornecedorDTO fornecedorDTO;
                    categoriaProduto.setCodCategoria((row[0]));
                    categoriaProduto.setNomeCategoria(row[1]);
                    fornecedorDTO = fornecedorService.findById(Long.parseLong(row[2]));
                    fornecedor.setId(fornecedorDTO.getIdFornecedor());
                    categoriaProduto.setFornecedor(fornecedor);
                    categoriaProdutos.add(categoriaProduto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        return iCategoriaProdutoRepository.saveAll(categoriaProdutos);
    }

    public CategoriaProdutoDTO save(CategoriaProdutoDTO categoriaProdutoDTO) {
        Fornecedor findFornecedorid = fornecedorService.findFornecedorById(categoriaProdutoDTO.getIdFornecedor());

        this.validate(categoriaProdutoDTO);

        LOGGER.info("Salvando Categoria do produto");
        LOGGER.debug("Categoria do Produto: {}", categoriaProdutoDTO);

        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setCodCategoria(categoriaProdutoDTO.getCodCategoria());
        categoriaProduto.setNomeCategoria(categoriaProdutoDTO.getNomeCategoria());
        categoriaProduto.setFornecedor(findFornecedorid);
        categoriaProduto = this.iCategoriaProdutoRepository.save(categoriaProduto);
        return CategoriaProdutoDTO.of(categoriaProduto);
    }

    private void validate(CategoriaProdutoDTO categoriaProdutoDTO) {
        LOGGER.info("Validando Categoria do Produto");
        if (categoriaProdutoDTO == null) {
            throw new IllegalArgumentException("CategoriaProdutoDTO não deve ser nulo");
        }
        if (StringUtils.isEmpty(categoriaProdutoDTO.getCodCategoria())) {
            throw new IllegalArgumentException("Código da categoria não deve ser nula/vazia");
        }
        if (StringUtils.isEmpty(categoriaProdutoDTO.getNomeCategoria())) {
            throw new IllegalArgumentException("Nome da categoria não deve ser nula/vazia");
        }

    }

    public CategoriaProdutoDTO findById(Long id) {
        Optional<CategoriaProduto> produtoOptional = this.iCategoriaProdutoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            return CategoriaProdutoDTO.of(produtoOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public CategoriaProduto findCategoriaProdutoById(long id) {
        Optional<CategoriaProduto> categoriaProdutoOptional = this.iCategoriaProdutoRepository.findById(id);
        if (categoriaProdutoOptional.isPresent()) {
            return categoriaProdutoOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public CategoriaProdutoDTO update(CategoriaProdutoDTO categoriaProdutoDTO, Long id) {
        Optional<CategoriaProduto> produtoExistenteOptional = this.iCategoriaProdutoRepository.findById(id);
        Fornecedor findFornecedorid = fornecedorService.findFornecedorById(categoriaProdutoDTO.getIdFornecedor());
        if (produtoExistenteOptional.isPresent()) {
            CategoriaProduto categoriaProdutoExistente = produtoExistenteOptional.get();

            LOGGER.info("Atualizando Categoria do produto... id: [{}]", categoriaProdutoExistente.getId());
            LOGGER.debug("Payload: {}", categoriaProdutoDTO);
            LOGGER.debug("Categoria do Produto Existente: {}", categoriaProdutoExistente);

            categoriaProdutoExistente.setCodCategoria(categoriaProdutoDTO.getCodCategoria());
            categoriaProdutoExistente.setNomeCategoria(categoriaProdutoDTO.getNomeCategoria());
            categoriaProdutoExistente.setFornecedor(findFornecedorid);
            categoriaProdutoExistente = this.iCategoriaProdutoRepository.save(categoriaProdutoExistente);
            return CategoriaProdutoDTO.of(categoriaProdutoExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Categoria do Produto de ID: [{}]", id);
        this.iCategoriaProdutoRepository.deleteById(id);
    }
}

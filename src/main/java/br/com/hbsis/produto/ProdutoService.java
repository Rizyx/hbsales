package br.com.hbsis.produto;

import br.com.hbsis.linhacategoria.LinhaCategoria;
import br.com.hbsis.linhacategoria.LinhaCategoriaDTO;
import br.com.hbsis.linhacategoria.LinhaCategoriaService;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaCategoriaService.class);
    private final IProdutoRepository iProdutoRepository;
    private final LinhaCategoriaService linhaCategoriaService;

    public ProdutoService(IProdutoRepository iProdutoRepository, LinhaCategoriaService linhaCategoriaService) {
        this.iProdutoRepository = iProdutoRepository;
        this.linhaCategoriaService = linhaCategoriaService;
    }

    public List<Produto> findAll() {
        List<Produto> produtos = iProdutoRepository.findAll();
        return produtos;
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        LinhaCategoria findLinhaCategoriaId = linhaCategoriaService.findLinhaCategoriaById(produtoDTO.getLinhaCategoria());

        this.validate(produtoDTO);

        LOGGER.info("Salvando produto");
        LOGGER.debug("Produto: {}", produtoDTO);

        Produto produto = new Produto();
        produto.setCodProduto(produtoDTO.getCodProduto());
        produto.setNomeProduto(produtoDTO.getNomeProduto());
        produto.setPrecoProduto(produtoDTO.getPrecoProduto());
        produto.setLinhaCategoria(findLinhaCategoriaId);
        produto.setUnidadeCaixaProduto(produtoDTO.getUnidadeCaixaProduto());
        produto.setPesoUnidade(produtoDTO.getPesoUnidade());
        produto.setValidade(produtoDTO.getValidade());
        produto = this.iProdutoRepository.save(produto);
        return produtoDTO.of(produto);
    }

    private void validate(ProdutoDTO produtoDTO) {
        LOGGER.info("Validando Produto");
        if (produtoDTO == null) {
            throw new IllegalArgumentException("linhaCategoriaDTO não deve ser nulo");
        }
        if (StringUtils.isEmpty(produtoDTO.getCodProduto())) {
            throw new IllegalArgumentException("Codigo do produto não deve ser nula/vazia");
        }
        if (StringUtils.isEmpty(produtoDTO.getNomeProduto())) {
            throw new IllegalArgumentException("Nome do produto não deve ser nula/vazia");
        }
        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getPrecoProduto()))) {
            throw new IllegalArgumentException("Preço do produto não deve ser nula/vazia");
        }
    }

    public ProdutoDTO findById(Long id) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            return ProdutoDTO.of(produtoOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public ProdutoDTO update(ProdutoDTO produtoDTO, Long id) {
        Optional<Produto> produtoExistenteOptional = this.iProdutoRepository.findById(id);
        LinhaCategoria findCategoriaProdutoid = linhaCategoriaService.findLinhaCategoriaById(produtoDTO.getLinhaCategoria());
        if (produtoExistenteOptional.isPresent()) {
            Produto produtoExistente = produtoExistenteOptional.get();

            LOGGER.info("Atualizando produto... id: [{}]", produtoExistente.getId());
            LOGGER.debug("Payload: {}", produtoDTO);
            LOGGER.debug("Produto Existente: {}", produtoExistente);

            produtoExistente.setCodProduto(produtoDTO.getCodProduto());
            produtoExistente.setNomeProduto(produtoDTO.getNomeProduto());
            produtoExistente.setPrecoProduto(produtoDTO.getPrecoProduto());
            produtoExistente.setLinhaCategoria(findCategoriaProdutoid);
            produtoExistente.setUnidadeCaixaProduto(produtoDTO.getUnidadeCaixaProduto());
            produtoExistente.setPrecoProduto(produtoDTO.getPrecoProduto());
            produtoExistente.setValidade(produtoDTO.getValidade());
            produtoExistente = this.iProdutoRepository.save(produtoExistente);
            return ProdutoDTO.of(produtoExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Produto de ID: [{}]", id);
        this.iProdutoRepository.deleteById(id);
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
        for (Produto produto : iProdutoRepository.findAll()) {
            Writer.writeNext(new String[]
                    {produto.getCodProduto(), produto.getNomeProduto(), String.valueOf(produto.getPrecoProduto()),
                            produto.getLinhaCategoria().getId().toString(),
                            String.valueOf(produto.getUnidadeCaixaProduto()),
                            String.valueOf(produto.getPesoUnidade()), String.valueOf(produto.getValidade())});
        }
    }

    public List<Produto> importCSV(MultipartFile file) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).build();
        List<String[]> rows = csvReader.readAll();
        List<Produto> produtos = new ArrayList<>();
        for (String[] row : rows) {
            try {
                Produto produto = new Produto();
                LinhaCategoria linhaCategoria = new LinhaCategoria();
                LinhaCategoriaDTO linhaCategoriaDTO;
                produto.setId(Long.parseLong(row[0]));
                Optional<Produto> produtoExistenteOptional = this.iProdutoRepository.findById(produto.getId());
                if(produtoExistenteOptional.isPresent()){
                    produto.setCodProduto(row[1]);
                    produto.setNomeProduto(row[2]);
                    produto.setPrecoProduto(Double.parseDouble(row[3]));
                    linhaCategoriaDTO = linhaCategoriaService.findById(Long.parseLong(row[4]));
                    linhaCategoria.setId(linhaCategoriaDTO.getIdLinhaCategoria());
                    produto.setUnidadeCaixaProduto(Long.parseLong(row[5]));
                    produto.setPesoUnidade(Double.parseDouble(row[6]));
                    produto.setValidade(LocalDateTime.from(formatter.parse(row[7])));
                    produto.setLinhaCategoria(linhaCategoria);
                    update(ProdutoDTO.of(produto),produto.getId());
                }else{
                    Produto produto2 = new Produto();
                    LinhaCategoria linhaCategoria2 = new LinhaCategoria();
                    LinhaCategoriaDTO linhaCategoriaDTO2;
                    produto2.setCodProduto(row[0]);
                    produto2.setNomeProduto(row[1]);
                    produto2.setPrecoProduto(Double.parseDouble(row[2]));
                    linhaCategoriaDTO2 = linhaCategoriaService.findById(Long.parseLong(row[3]));
                    linhaCategoria2.setId(linhaCategoriaDTO2.getIdLinhaCategoria());
                    produto2.setUnidadeCaixaProduto(Long.parseLong(row[4]));
                    produto2.setPesoUnidade(Double.parseDouble(row[5]));
                    produto2.setValidade(LocalDateTime.from(formatter.parse(row[6])));
                    produto2.setLinhaCategoria(linhaCategoria2);
                    produtos.add(produto2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iProdutoRepository.saveAll(produtos);
    }
}

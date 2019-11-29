package br.com.hbsis.produto;

import br.com.hbsis.linhaCategoria.LinhaCategoria;
import br.com.hbsis.linhaCategoria.LinhaCategoriaDTO;
import br.com.hbsis.linhaCategoria.LinhaCategoriaService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.sql.Date;
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
    public  List<Produto> saveAll(List<Produto> produtos)  {
        return iProdutoRepository.saveAll(produtos);
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
    }
    public ProdutoDTO findById(Long id) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return ProdutoDTO.of(produtoOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }
    public ProdutoDTO update(ProdutoDTO produtoDTO, Long id_produto) {
        Optional<Produto> produtoExistenteOptional = this.iProdutoRepository.findById(id_produto);
        LinhaCategoria findCategoriaProdutoid = linhaCategoriaService.findLinhaCategoriaById(produtoDTO.getLinhaCategoria());
        if (produtoExistenteOptional.isPresent()) {
            Produto produtoExistente = produtoExistenteOptional.get();

            LOGGER.info("Atualizando produto... id: [{}]", produtoExistente.getId_produto());
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
        throw new IllegalArgumentException(String.format("ID %s não existe", id_produto));
    }
    public void delete(Long id_produto) {
        LOGGER.info("Executando delete para Produto de ID: [{}]", id_produto);

        this.iProdutoRepository.deleteById(id_produto);
    }
    public List<Produto> importCSV(MultipartFile file) throws Exception {

        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).build();
        List<String[]> rows = csvReader.readAll();
        List<Produto> produtos = new ArrayList<>();
        for (String[] row : rows) {
            try {
                Produto produto = new Produto();
                LinhaCategoria linhaCategoria = new LinhaCategoria();
                LinhaCategoriaDTO linhaCategoriaDTO;
                produto.setCodProduto((row[0]));
                produto.setNomeProduto(row[1]);
                produto.setPrecoProduto(Double.parseDouble(row[2]));
                linhaCategoriaDTO = linhaCategoriaService.findById(Long.parseLong(row[3]));
                linhaCategoria.setId_linha_categoria(linhaCategoriaDTO.getId_linha_categoria());
                produto.setUnidadeCaixaProduto(Long.parseLong(row[4]));
                produto.setPesoUnidade(Double.parseDouble(row[5]));
                produto.setValidade(Date.valueOf(row[6]));
                produto.setLinhaCategoria(linhaCategoria);
                produtos.add(produto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iProdutoRepository.saveAll(produtos);
    }
}

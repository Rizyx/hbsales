package br.com.hbsis.fornecedor;

import br.com.hbsis.categoriaproduto.CategoriaProduto;
import br.com.hbsis.categoriaproduto.CategoriaProdutoDTO;
import br.com.hbsis.categoriaproduto.CategoriaProdutoService;
import br.com.hbsis.categoriaproduto.ICategoriaProdutoRepository;
import br.com.hbsis.linhacategoria.ILinhaCategoriaRepository;
import br.com.hbsis.linhacategoria.LinhaCategoria;
import br.com.hbsis.linhacategoria.LinhaCategoriaDTO;
import br.com.hbsis.linhacategoria.LinhaCategoriaService;
import br.com.hbsis.produto.IProdutoRepository;
import br.com.hbsis.produto.Produto;
import br.com.hbsis.produto.ProdutoDTO;
import br.com.hbsis.produto.ProdutoService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorService.class);
    private final IFornecedorRepository iFornecedorRepository;
    private final ProdutoService produtoService;
    private final LinhaCategoriaService linhaCategoriaService;
    private final CategoriaProdutoService categoriaProdutoService;
    private final IProdutoRepository iProdutoRepository;
    private final ILinhaCategoriaRepository iLinhaCategoriaRepository;
    private final ICategoriaProdutoRepository iCategoriaProdutoRepository;

    public FornecedorService(IFornecedorRepository iFornecedorRepository, @Lazy ProdutoService produtoService, @Lazy LinhaCategoriaService linhaCategoriaService,
                             @Lazy CategoriaProdutoService categoriaProdutoService, IProdutoRepository iProdutoRepository, ILinhaCategoriaRepository iLinhaCategoriaRepository,ICategoriaProdutoRepository iCategoriaProdutoRepository) {
        this.iFornecedorRepository = iFornecedorRepository;
        this.produtoService = produtoService;
        this.linhaCategoriaService = linhaCategoriaService;
        this.categoriaProdutoService = categoriaProdutoService;
        this.iProdutoRepository = iProdutoRepository;
        this.iLinhaCategoriaRepository = iLinhaCategoriaRepository;
        this.iCategoriaProdutoRepository = iCategoriaProdutoRepository;
    }

    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        this.validate(fornecedorDTO);

        LOGGER.info("Salvando fornecedor");
        LOGGER.debug("Fornecedor: {}", fornecedorDTO);

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setRazaoSocial(fornecedorDTO.getRazaoSocial());
        fornecedor.setCnpj(fornecedorDTO.getCnpj());
        fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setTelefoneContato(fornecedorDTO.getTelefoneContato());
        fornecedor.setEmailContato(fornecedorDTO.getEmailContato());
        fornecedor = this.iFornecedorRepository.save(fornecedor);

        return FornecedorDTO.of(fornecedor);
    }

    private void validate(FornecedorDTO fornecedorDTO) {
        LOGGER.info("Validando Fornecedor");
        if (fornecedorDTO == null) {
            throw new IllegalArgumentException("FornecedorDTO não deve ser nulo");
        }
        if (StringUtils.isEmpty(fornecedorDTO.getRazaoSocial())) {
            throw new IllegalArgumentException("Razão Social não deve ser nula/vazia");
        }
        if (StringUtils.isEmpty(String.valueOf(fornecedorDTO.getCnpj()))) {
            throw new IllegalArgumentException("CNPJ não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(fornecedorDTO.getNomeFantasia())) {
            throw new IllegalArgumentException("Nome fantasia não deve ser nulo/vazio");
        }
    }

    public FornecedorDTO findById(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);
        if (fornecedorOptional.isPresent()) {
            return FornecedorDTO.of(fornecedorOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public FornecedorDTO findByCnpj(Long cnpj) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findByCnpj(cnpj);
        if (fornecedorOptional.isPresent()) {
            return FornecedorDTO.of(fornecedorOptional.get());
        }
        throw new IllegalArgumentException(String.format("cnpj %s não existe", cnpj));
    }

    public Fornecedor findFornecedorById(long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);
        if (fornecedorOptional.isPresent()) {
            return fornecedorOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public FornecedorDTO update(FornecedorDTO fornecedorDTO, Long id) {
        Optional<Fornecedor> fornecedorExistenteOptional = this.iFornecedorRepository.findById(id);
        if (fornecedorExistenteOptional.isPresent()) {
            Fornecedor fornecedorExistente = fornecedorExistenteOptional.get();

            LOGGER.info("Atualizando fornecedor... id: [{}]", fornecedorExistente.getId());
            LOGGER.debug("Payload: {}", fornecedorDTO);
            LOGGER.debug("Fornecedor Existente: {}", fornecedorExistente);

            fornecedorExistente.setRazaoSocial(fornecedorDTO.getRazaoSocial());
            fornecedorExistente.setCnpj(fornecedorDTO.getCnpj());
            fornecedorExistente.setNomeFantasia(fornecedorDTO.getNomeFantasia());
            fornecedorExistente.setEndereco(fornecedorDTO.getEndereco());
            fornecedorExistente.setTelefoneContato(fornecedorDTO.getTelefoneContato());
            fornecedorExistente.setEmailContato(fornecedorDTO.getEmailContato());
            fornecedorExistente = this.iFornecedorRepository.save(fornecedorExistente);

            return FornecedorDTO.of(fornecedorExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public List<Fornecedor> findAll() {
        List<Fornecedor> fornecedors = iFornecedorRepository.findAll();
        return fornecedors;
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Fornecedor de ID: [{}]", id);
        this.iFornecedorRepository.deleteById(id);
    }

    private static DateTimeFormatter formatter =
            new DateTimeFormatterBuilder().appendPattern("dd-MM-yyyy[ [HH][:mm][:ss][.SSS]]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter();

    public List<Produto> saveImportById(Long id , MultipartFile file) throws Exception {
        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        List<String[]> rows = csvReader.readAll();
        List<Produto> produtos = new ArrayList<>();
        for (String[] row : rows) {
            String[] rowTemp = row[0].replaceAll("[\"]", "").split(";");
            try {
                Produto produto = new Produto();
                Fornecedor fornecedor = new Fornecedor();
                LinhaCategoria linhaCategoria = new LinhaCategoria();
                CategoriaProduto categoriaProduto = new CategoriaProduto();
                ProdutoDTO produtoDTO;
                Optional<Produto> produtoOptional = this.iProdutoRepository.findByCodProduto(rowTemp[0]);
                if(produtoOptional.isPresent()){
                    produtoDTO = produtoService.findByCodProduto(rowTemp[0]);
                    produto.setCodProduto(rowTemp[0]);
                    produto.setId(produtoDTO.getIdProduto());
                    produto.setNomeProduto(rowTemp[1]);
                    produto.setPrecoProduto(Double.parseDouble(rowTemp[2].replace("R$", "").replace(",", ".")));
                    produto.setUnidadeCaixaProduto(Long.parseLong(rowTemp[3]));
                    produto.setPesoUnidade(Double.parseDouble(rowTemp[4]));
                    produto.setMedidaPeso(rowTemp[5]);
                    produto.setValidade(LocalDateTime.from(formatter.parse(rowTemp[6])));
                    produto.setLinhaCategoria(linhaCategoriaService.findByCodLinhaCategoria(rowTemp[7]));
                    produtoService.update(produtoDTO.of(produto), produtoDTO.getIdProduto());
                }else{
                    produto.setCodProduto(rowTemp[1]);
                    produto.setNomeProduto(rowTemp[1]);
                    produto.setPrecoProduto(Double.parseDouble(rowTemp[2].replace("R$", "").replace(",", ".")));
                    produto.setUnidadeCaixaProduto(Long.parseLong(rowTemp[3]));
                    produto.setPesoUnidade(Double.parseDouble(rowTemp[4]));
                    produto.setMedidaPeso(rowTemp[5]);
                    produto.setValidade(LocalDateTime.from(formatter.parse(rowTemp[6])));
                    Optional<CategoriaProduto> categoriaProdutoOptional = this.iCategoriaProdutoRepository.findByCodCategoria(rowTemp[9]);
                    if(categoriaProdutoOptional.isPresent()){
                        categoriaProduto = categoriaProdutoService.findByCodCategoria(rowTemp[9]);
                        categoriaProduto.setId(categoriaProduto.getId());
                        categoriaProduto.setNomeCategoria(rowTemp[10]);
                    }else{
                        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);
                        fornecedor = fornecedorOptional.get();
                        categoriaProduto.setCodCategoria(rowTemp[7]);
                        categoriaProduto.setNomeCategoria(rowTemp[10]);
                        categoriaProduto.setFornecedor(fornecedor);
                        iCategoriaProdutoRepository.save(categoriaProduto);
                    }
                Optional<LinhaCategoria> linhaCategoriaOptional = this.iLinhaCategoriaRepository.findByCodLinhaCategoria(rowTemp[7]);
                if(linhaCategoriaOptional.isPresent()){
                    linhaCategoria = linhaCategoriaService.findByCodLinhaCategoria((rowTemp[7]));
                    linhaCategoria.setId(linhaCategoria.getId());
                    linhaCategoria.setCategoriaProduto(categoriaProduto);
                    linhaCategoriaService.update(LinhaCategoriaDTO.of(linhaCategoria), linhaCategoria.getId());
                    return iProdutoRepository.saveAll(produtos);
                }else{
                    linhaCategoria.setCodLinhaCategoria(rowTemp[7]);
                    linhaCategoria.setNomeLinha(rowTemp[8]);
                    linhaCategoria.setCategoriaProduto(categoriaProduto);
                    iLinhaCategoriaRepository.save(linhaCategoria);
                    produto.setLinhaCategoria(linhaCategoria);
                    produtos.add(produto);
                return iProdutoRepository.saveAll(produtos);}}
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return produtos;
    }
}


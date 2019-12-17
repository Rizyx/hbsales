package br.com.hbsis.categoriaproduto;

import br.com.hbsis.fornecedor.Fornecedor;
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
                .withSeparator(';')
                .withEscapeChar(CSVWriter.NO_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();
        String column[] = {"id", "cod_categoria", "nome_categoria", "fornecedor_categoria"};
        Writer.writeNext(column);

        for (CategoriaProduto categoriaProduto : iCategoriaProdutoRepository.findAll()) {
            Writer.writeNext(new String[]
                    {categoriaProduto.getCodCategoria(),categoriaProduto.getNomeCategoria(),
                            categoriaProduto.getFornecedor().getRazaoSocial(),
                            categoriaProduto.getFornecedor().getCnpj().toString().substring(0,2)+"."+categoriaProduto.getFornecedor().getCnpj().toString().substring(2,5)+"."+categoriaProduto.getFornecedor().getCnpj().toString().substring(5,8)+"/"+categoriaProduto.getFornecedor().getCnpj().toString().substring(8,12)+"-"+categoriaProduto.getFornecedor().getCnpj().toString().substring(12,14)});
        }
    }

    public List<CategoriaProduto> importCSV(MultipartFile file) throws Exception {
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            List<String[]> rows = csvReader.readAll();
            List<CategoriaProduto> categoriaProdutos = new ArrayList<>();
            for (String[] row : rows) {
                String[] rowTemp = row[0].replaceAll("[-\"./]", "").split(";");
                try {
                    CategoriaProduto categoriaProduto = new CategoriaProduto();
                    Fornecedor fornecedor;
                    categoriaProduto.setCodCategoria((rowTemp[0]));
                    Optional<CategoriaProduto> categoriaProdutoOptional = this.iCategoriaProdutoRepository.findByCodCategoria(categoriaProduto.getCodCategoria());
                    if(categoriaProdutoOptional.isPresent()){
                    }else{
                    categoriaProduto.setNomeCategoria(rowTemp[1]);
                    fornecedor = fornecedorService.findByCnpj(Long.parseLong(rowTemp[3]));
                    rowTemp[2] = String.valueOf(fornecedor.getId());
                    fornecedor.setId(Long.parseLong(rowTemp[2]));
                    Optional<Fornecedor> fornecedorOptional = Optional.ofNullable(this.fornecedorService.findByCnpj(fornecedor.getCnpj()));
                    if(fornecedorOptional.isPresent()){
                    }else{
                    categoriaProduto.setFornecedor(fornecedor);
                    categoriaProdutos.add(categoriaProduto);
                    return iCategoriaProdutoRepository.saveAll(categoriaProdutos);}}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        return categoriaProdutos;
    }

    public CategoriaProdutoDTO save(CategoriaProdutoDTO categoriaProdutoDTO) {
        Fornecedor findFornecedorid = fornecedorService.findFornecedorById(categoriaProdutoDTO.getIdFornecedor());

        this.validate(categoriaProdutoDTO);

        LOGGER.info("Salvando Categoria do produto");
        LOGGER.debug("Categoria do Produto: {}", categoriaProdutoDTO);
        String codigo="";
        CategoriaProduto categoriaProduto = new CategoriaProduto();
            categoriaProduto.setFornecedor(findFornecedorid);
            categoriaProduto.setNomeCategoria(categoriaProdutoDTO.getNomeCategoria());
            categoriaProduto.setCodCategoria("CAT" + categoriaProduto.getFornecedor().getCnpj().toString().substring(10,14) + categoriaProdutoDTO.getCodCategoria().toUpperCase());
            while (categoriaProduto.getCodCategoria().length() < 10) {
            codigo += "0";
                categoriaProduto.setCodCategoria("CAT" + categoriaProduto.getFornecedor().getCnpj().toString().substring(10,14) + codigo + categoriaProdutoDTO.getCodCategoria().toUpperCase());
        } if(categoriaProduto.getCodCategoria().length() > 10) {
            throw new IllegalArgumentException("Codigo invalido digite novamente");
        }else{
        categoriaProduto = this.iCategoriaProdutoRepository.save(categoriaProduto);
        }
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

    public CategoriaProduto findByCodCategoria(String codCategoria) {
        Optional<CategoriaProduto> categoriaProdutoOptional = this.iCategoriaProdutoRepository.findByCodCategoria(codCategoria);
        if (categoriaProdutoOptional.isPresent()) {
            return categoriaProdutoOptional.get();
        }
        throw new IllegalArgumentException(String.format("cnpj %s não existe", codCategoria));
    }

    public CategoriaProduto findCategoriaProdutoById(long id) {
        Optional<CategoriaProduto> categoriaProdutoOptional = this.iCategoriaProdutoRepository.findById(id);
        if (categoriaProdutoOptional.isPresent()) {
            return categoriaProdutoOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Optional<CategoriaProduto> bDuse (String codCat) {
        Optional<CategoriaProduto> findByCodCat = this.iCategoriaProdutoRepository.findByCodCategoria(codCat);
        return findByCodCat;
    }

    public CategoriaProdutoDTO update(CategoriaProdutoDTO categoriaProdutoDTO, Long id) {
        Optional<CategoriaProduto> produtoExistenteOptional = this.iCategoriaProdutoRepository.findById(id);
        Fornecedor findFornecedorid = fornecedorService.findFornecedorById(categoriaProdutoDTO.getIdFornecedor());
        if (produtoExistenteOptional.isPresent()) {
            CategoriaProduto categoriaProdutoExistente = produtoExistenteOptional.get();

            LOGGER.info("Atualizando Categoria do produto... id: [{}]", categoriaProdutoExistente.getId());
            LOGGER.debug("Payload: {}", categoriaProdutoDTO);
            LOGGER.debug("Categoria do Produto Existente: {}", categoriaProdutoExistente);
            String codigo = "";
            categoriaProdutoExistente.setFornecedor(findFornecedorid);
            categoriaProdutoExistente.setNomeCategoria(categoriaProdutoDTO.getNomeCategoria());
            categoriaProdutoExistente.setCodCategoria("CAT" + categoriaProdutoExistente.getFornecedor().getCnpj().toString().toUpperCase().substring(10, 14) + categoriaProdutoDTO.getCodCategoria().toUpperCase());
            while (categoriaProdutoExistente.getCodCategoria().length() < 10) {
                codigo += "0";
                categoriaProdutoExistente.setCodCategoria("CAT" + categoriaProdutoExistente.getFornecedor().getCnpj().toString().substring(10, 14) + codigo + categoriaProdutoDTO.getCodCategoria().toUpperCase());
                if (categoriaProdutoExistente.getCodCategoria().length() > 10) {
                    throw new IllegalArgumentException("Codigo invalido digite novamente");
                } else {
                    return CategoriaProdutoDTO.of(categoriaProdutoExistente);
                }
            }
        }
        return categoriaProdutoDTO;
    }
    public void delete(Long id) {
        LOGGER.info("Executando delete para Categoria do Produto de ID: [{}]", id);
        this.iCategoriaProdutoRepository.deleteById(id);
    }
}

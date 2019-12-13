package br.com.hbsis.categoriaproduto;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.fornecedor.IFornecedorRepository;
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
    private final IFornecedorRepository iFornecedorRepository;

    public CategoriaProdutoService(ICategoriaProdutoRepository iCategoriaProdutoRepository, FornecedorService fornecedorService, IFornecedorRepository iFornecedorRepository) {
        this.iCategoriaProdutoRepository = iCategoriaProdutoRepository;
        this.fornecedorService = fornecedorService;
        this.iFornecedorRepository = iFornecedorRepository;
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
                    Fornecedor fornecedor = new Fornecedor();
                    FornecedorDTO fornecedorDTO;
                    categoriaProduto.setCodCategoria((rowTemp[0]));
                    Optional<CategoriaProduto> categoriaProdutoOptional = this.iCategoriaProdutoRepository.findByCodCategoria(categoriaProduto.getCodCategoria());
                    if(categoriaProdutoOptional.isPresent()){

                    }else{
                    categoriaProduto.setNomeCategoria(rowTemp[1]);
                    fornecedorDTO = fornecedorService.findByCnpj(Long.parseLong(rowTemp[3]));
                    rowTemp[2] = String.valueOf(fornecedorDTO.getIdFornecedor());
                    fornecedor.setId(Long.parseLong(rowTemp[2]));
                    Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findByCnpj(fornecedor.getCnpj());
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

        CategoriaProduto categoriaProduto = new CategoriaProduto();
            categoriaProduto.setFornecedor(findFornecedorid);
            categoriaProduto.setNomeCategoria(categoriaProdutoDTO.getNomeCategoria());
            categoriaProduto.setCodCategoria("CAT" + categoriaProduto.getFornecedor().getCnpj().toString().substring(10,14) + categoriaProdutoDTO.getCodCategoria());
        if(categoriaProduto.getCodCategoria().length() > 10) {
            throw new IllegalArgumentException("Codigo invalido digite novamente");
        }else if(categoriaProduto.getCodCategoria().length() == 9 ){
            categoriaProduto.setCodCategoria("CAT" + categoriaProduto.getFornecedor().getCnpj().toString().substring(10,14) + "0" + categoriaProdutoDTO.getCodCategoria());
            categoriaProduto = this.iCategoriaProdutoRepository.save(categoriaProduto);
        }else if(categoriaProduto.getCodCategoria().length() == 8 ){
            categoriaProduto.setCodCategoria("CAT" + categoriaProduto.getFornecedor().getCnpj().toString().substring(10,14) + "00" + categoriaProdutoDTO.getCodCategoria());
            categoriaProduto = this.iCategoriaProdutoRepository.save(categoriaProduto);
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

    public CategoriaProdutoDTO update(CategoriaProdutoDTO categoriaProdutoDTO, Long id) {
        Optional<CategoriaProduto> produtoExistenteOptional = this.iCategoriaProdutoRepository.findById(id);
        Fornecedor findFornecedorid = fornecedorService.findFornecedorById(categoriaProdutoDTO.getIdFornecedor());
        if (produtoExistenteOptional.isPresent()) {
            CategoriaProduto categoriaProdutoExistente = produtoExistenteOptional.get();

            LOGGER.info("Atualizando Categoria do produto... id: [{}]", categoriaProdutoExistente.getId());
            LOGGER.debug("Payload: {}", categoriaProdutoDTO);
            LOGGER.debug("Categoria do Produto Existente: {}", categoriaProdutoExistente);

            categoriaProdutoExistente.setFornecedor(findFornecedorid);
            categoriaProdutoExistente.setNomeCategoria(categoriaProdutoDTO.getNomeCategoria());
            categoriaProdutoExistente.setCodCategoria("CAT" + categoriaProdutoExistente.getFornecedor().getCnpj().toString().toUpperCase().substring(10,14) + categoriaProdutoDTO.getCodCategoria());
            if(categoriaProdutoExistente.getCodCategoria().length() > 10) {
                throw new IllegalArgumentException("Codigo invalido digite novamente");
            }else if(categoriaProdutoExistente.getCodCategoria().length() == 9 ){
                categoriaProdutoExistente.setCodCategoria("CAT" + categoriaProdutoExistente.getFornecedor().getCnpj().toString().substring(10,13) + "0" + categoriaProdutoDTO.getCodCategoria());
            }else if(categoriaProdutoExistente.getCodCategoria().length() == 8 ) {
                categoriaProdutoExistente.setCodCategoria("CAT" + categoriaProdutoExistente.getFornecedor().getCnpj().toString().substring(10, 13) + "00" + categoriaProdutoDTO.getCodCategoria());
                return CategoriaProdutoDTO.of(categoriaProdutoExistente);
            }else{
                return CategoriaProdutoDTO.of(categoriaProdutoExistente);
                }
            throw new IllegalArgumentException(String.format("ID %s não existe", id));
        }
        return categoriaProdutoDTO;
      }


    public void delete(Long id) {
        LOGGER.info("Executando delete para Categoria do Produto de ID: [{}]", id);
        this.iCategoriaProdutoRepository.deleteById(id);
    }
}

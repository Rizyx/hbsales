package br.com.hbsis.categoriaProduto;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.InputStreamReader;
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
    public  List<CategoriaProduto> saveAll(List<CategoriaProduto> categoriaProdutos)  {

        return iCategoriaProdutoRepository.saveAll(categoriaProdutos);
    }

    public boolean importExcel(MultipartFile file) {
        Workbook workbook = getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();
        rows.next();
        while(rows.hasNext()) {
            Row row = rows.next();
            CategoriaProduto categoriaProduto = new CategoriaProduto();
            if (row.getCell(0).getCellType() == CellType.STRING) {
                categoriaProduto.setCodCategoria(row.getCell(0).getStringCellValue());
            }
            if (row.getCell(1).getCellType() == CellType.STRING) {
                categoriaProduto.setNomeCategoria(row.getCell(1).getStringCellValue());
            }

            if (row.getCell(2).getCellType() == CellType.NUMERIC) {
                categoriaProduto.getFornecedor().getId_fornecedor();
            }
            iCategoriaProdutoRepository.save(categoriaProduto);
        }
        return true;
    }

    public Workbook getWorkBook(MultipartFile file) {
        Workbook workbook = null;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        try{
            if(extension.equalsIgnoreCase("xlsx")){
                workbook = new XSSFWorkbook(file.getInputStream());
            }else if (extension.equalsIgnoreCase("xls")){
                workbook = new HSSFWorkbook(file.getInputStream());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return workbook;
    }

    public boolean importJson(MultipartFile file) {
        try{
            InputStream inputStream = file.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            List<CategoriaProduto> categoriaProdutos = Arrays.asList(mapper.readValue(inputStream, CategoriaProduto[].class));
            if(categoriaProdutos != null && categoriaProdutos.size()>0){
                for(CategoriaProduto categoriaProduto : categoriaProdutos) {
                    iCategoriaProdutoRepository.save(categoriaProduto);
                }
            }
            return true;
        }catch (Exception e){
            return false;
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
                    fornecedor.setId_fornecedor(fornecedorDTO.getIdFornecedor());
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

        LOGGER.info("Salvando produto");
        LOGGER.debug("Produto: {}", categoriaProdutoDTO);

        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setCodCategoria(categoriaProdutoDTO.getCodCategoria());
        categoriaProduto.setNomeCategoria(categoriaProdutoDTO.getNomeCategoria());
        categoriaProduto.setFornecedor(findFornecedorid);


        categoriaProduto = this.iCategoriaProdutoRepository.save(categoriaProduto);

        return CategoriaProdutoDTO.of(categoriaProduto);
    }

    private void validate(CategoriaProdutoDTO categoriaProdutoDTO) {
        LOGGER.info("Validando Produto");

        if (categoriaProdutoDTO == null) {
            throw new IllegalArgumentException("ProdutoDTO não deve ser nulo");
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
    public CategoriaProduto findCategoriaProdutoById(long id_categoria_produtos) {
        Optional<CategoriaProduto> categoriaProdutoOptional = this.iCategoriaProdutoRepository.findById(id_categoria_produtos);


        if (categoriaProdutoOptional.isPresent()) {
            return categoriaProdutoOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id_categoria_produtos));
    }


    public CategoriaProdutoDTO update(CategoriaProdutoDTO categoriaProdutoDTO, Long id) {
        Optional<CategoriaProduto> produtoExistenteOptional = this.iCategoriaProdutoRepository.findById(id);
        Fornecedor findFornecedorid = fornecedorService.findFornecedorById(categoriaProdutoDTO.getIdFornecedor());
        if (produtoExistenteOptional.isPresent()) {
            CategoriaProduto categoriaProdutoExistente = produtoExistenteOptional.get();

            LOGGER.info("Atualizando produto... id: [{}]", categoriaProdutoExistente.getId_categoria_produtos());
            LOGGER.debug("Payload: {}", categoriaProdutoDTO);
            LOGGER.debug("Produto Existente: {}", categoriaProdutoExistente);

            categoriaProdutoExistente.setCodCategoria(categoriaProdutoDTO.getCodCategoria());
            categoriaProdutoExistente.setNomeCategoria(categoriaProdutoDTO.getNomeCategoria());
            categoriaProdutoExistente.setFornecedor(findFornecedorid);

            categoriaProdutoExistente = this.iCategoriaProdutoRepository.save(categoriaProdutoExistente);

            return CategoriaProdutoDTO.of(categoriaProdutoExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));

    }

    public void delete(Long id_categoria_produtos) {
        LOGGER.info("Executando delete para Produto de ID: [{}]", id_categoria_produtos);

        this.iCategoriaProdutoRepository.deleteById(id_categoria_produtos);
    }
}

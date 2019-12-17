package br.com.hbsis.produto;

import br.com.hbsis.categoriaproduto.CategoriaProduto;
import br.com.hbsis.categoriaproduto.CategoriaProdutoDTO;
import br.com.hbsis.categoriaproduto.CategoriaProdutoService;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
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
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaCategoriaService.class);
    private final IProdutoRepository iProdutoRepository;
    private final LinhaCategoriaService linhaCategoriaService;
    private final CategoriaProdutoService categoriaProdutoService;
    private final FornecedorService fornecedorService;

    public ProdutoService(IProdutoRepository iProdutoRepository, LinhaCategoriaService linhaCategoriaService, CategoriaProdutoService categoriaProdutoService, FornecedorService fornecedorService) {
        this.iProdutoRepository = iProdutoRepository;
        this.linhaCategoriaService = linhaCategoriaService;
        this.categoriaProdutoService = categoriaProdutoService;
        this.fornecedorService = fornecedorService;
    }

    public List<Produto> findAll() {
        List<Produto> produtos = iProdutoRepository.findAll();
        return produtos;
    }

    public Produto findProdutoById(long id) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            return produtoOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public List<Produto> saveAll(List<Produto> produtos) {
        return iProdutoRepository.saveAll(produtos);
    }


    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        LinhaCategoria findLinhaCategoriaId = linhaCategoriaService.findLinhaCategoriaById(produtoDTO.getLinhaCategoria());

        this.validate(produtoDTO);

        LOGGER.info("Salvando produto");
        LOGGER.debug("Produto: {}", produtoDTO);
        String codigo = "";
        Produto produto = new Produto();
        produto.setCodProduto(produtoDTO.getCodProduto());
        while (produto.getCodProduto().length() < 10) {
            codigo += "0";
            produto.setCodProduto(codigo + produtoDTO.getCodProduto().toUpperCase());
        }
        if (produto.getCodProduto().length() > 10) {
            throw new IllegalArgumentException("codigo invalido");
        } else {
            produto.setNomeProduto(produtoDTO.getNomeProduto());
            produto.setPrecoProduto(produtoDTO.getPrecoProduto());
            produto.setLinhaCategoria(findLinhaCategoriaId);
            produto.setUnidadeCaixaProduto(produtoDTO.getUnidadeCaixaProduto());
            produto.setPesoUnidade(produtoDTO.getPesoUnidade());
            produto.setMedidaPeso(produtoDTO.getMedidaPeso());
            if (produto.getMedidaPeso().equalsIgnoreCase("mg")
                    || produto.getMedidaPeso().equalsIgnoreCase("kg")
                    || produto.getMedidaPeso().equalsIgnoreCase("g")) {
                produto.setValidade(produtoDTO.getValidade());
                produto = this.iProdutoRepository.save(produto);
                return produtoDTO.of(produto);
            } else {
                throw new IllegalArgumentException("Unidade de peso nao permitida");
            }
        }
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
        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getLinhaCategoria()))) {
            throw new IllegalArgumentException("Linha categoria nao pode ser nulo");
        }
        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getUnidadeCaixaProduto()))) {
            throw new IllegalArgumentException("Unidade por caixa nao pode ser nulo");
        }
        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getPesoUnidade()))) {
            throw new IllegalArgumentException("Peso do produto nao pode ser nulo");
        }
        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getMedidaPeso()))) {
            throw new IllegalArgumentException("Medida de peso nao pode ser nula");
        }
        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getValidade()))) {
            throw new IllegalArgumentException("Validade do produto nao pode ser nula");
        }


    }

    public ProdutoDTO findById(Long id) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            return ProdutoDTO.of(produtoOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Optional<Produto> BDuse(String codProd) {
        Optional<Produto> findByCodProd = this.iProdutoRepository.findByCodProduto(codProd);
        return findByCodProd;
    }

    public Produto findByCodProduto(String codProduto) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findByCodProduto(codProduto);
        if (produtoOptional.isPresent()) {
            return produtoOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", codProduto));
    }

    public ProdutoDTO update(ProdutoDTO produtoDTO, Long id) {
        Optional<Produto> produtoExistenteOptional = this.iProdutoRepository.findById(id);
        LinhaCategoria findCategoriaProdutoid = linhaCategoriaService.findLinhaCategoriaById(produtoDTO.getLinhaCategoria());
        if (produtoExistenteOptional.isPresent()) {
            Produto produtoExistente = produtoExistenteOptional.get();

            LOGGER.info("Atualizando produto... id: [{}]", produtoExistente.getId());
            LOGGER.debug("Payload: {}", produtoDTO);
            LOGGER.debug("Produto Existente: {}", produtoExistente);
            String codigo = "";
            produtoExistente.setCodProduto(produtoDTO.getCodProduto());
            while (produtoExistente.getCodProduto().length() < 10) {
                codigo += "0";
                produtoExistente.setCodProduto(codigo + produtoDTO.getCodProduto().toUpperCase());
            }
            if (produtoExistente.getCodProduto().length() > 10) {
                throw new IllegalArgumentException("codigo invalido");
            } else {
                produtoExistente.setNomeProduto(produtoDTO.getNomeProduto());
                produtoExistente.setPrecoProduto(produtoDTO.getPrecoProduto());
                produtoExistente.setLinhaCategoria(findCategoriaProdutoid);
                produtoExistente.setUnidadeCaixaProduto(produtoDTO.getUnidadeCaixaProduto());
                produtoExistente.setPrecoProduto(produtoDTO.getPrecoProduto());
                produtoExistente.setMedidaPeso(produtoDTO.getMedidaPeso());
                if (produtoExistente.getMedidaPeso().equalsIgnoreCase("mg")
                        || produtoExistente.getMedidaPeso().equalsIgnoreCase("kg")
                        || produtoExistente.getMedidaPeso().equalsIgnoreCase("g")) {
                    produtoExistente.setValidade(produtoDTO.getValidade());
                    produtoExistente = this.iProdutoRepository.save(produtoExistente);
                    return produtoDTO.of(produtoExistente);
                } else {
                    throw new IllegalArgumentException("Unidade de peso nao permitida");
                }
            }
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Produto de ID: [{}]", id);
        this.iProdutoRepository.deleteById(id);
    }

    public void exportCSV(HttpServletResponse response) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String filename = "ArquivoDeExpotação.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        PrintWriter writer = response.getWriter();
        ICSVWriter Writer = new CSVWriterBuilder(writer)
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();
        String column[] = {"codigo", "nome", "preco", "quantidade_por_caixa", "peso_por_unidade", "data_de_validade", "codigo_linha_categoria", "linha_categoria", "codigo_categoria", "categoria", "cnpj_fornecedor", "razao_social_fornecedor"};
        Writer.writeNext(column);
        for (Produto produto : iProdutoRepository.findAll()) {
            Writer.writeNext(new String[]
                    {produto.getCodProduto(), produto.getNomeProduto(), "R$" + (produto.getPrecoProduto()),
                            String.valueOf(produto.getUnidadeCaixaProduto()), produto.getPesoUnidade() + produto.getMedidaPeso(),
                            produto.getValidade().format(formatter), String.valueOf(produto.getLinhaCategoria().getCodLinhaCategoria()), produto.getLinhaCategoria().getNomeLinha(),
                            produto.getLinhaCategoria().getCategoriaProduto().getCodCategoria(), produto.getLinhaCategoria().getCategoriaProduto().getNomeCategoria(),
                            produto.getLinhaCategoria().getCategoriaProduto().getFornecedor().getCnpj().toString().substring(0, 2) + "." + produto.getLinhaCategoria().getCategoriaProduto().getFornecedor().getCnpj().toString().substring(2, 5) + "." + produto.getLinhaCategoria().getCategoriaProduto().getFornecedor().getCnpj().toString().substring(5, 8) + "/" + produto.getLinhaCategoria().getCategoriaProduto().getFornecedor().getCnpj().toString().substring(8, 12) + "-" + produto.getLinhaCategoria().getCategoriaProduto().getFornecedor().getCnpj().toString().substring(12, 14),
                            produto.getLinhaCategoria().getCategoriaProduto().getFornecedor().getRazaoSocial()});
        }
    }

    private static DateTimeFormatter formatter =
            new DateTimeFormatterBuilder().appendPattern("dd-MM-yyyy[ [HH][:mm][:ss][.SSS]]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter();

    public List<Produto> importCSV(MultipartFile file) throws Exception {
        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        List<String[]> rows = csvReader.readAll();
        List<Produto> produtos = new ArrayList<>();
        for (String[] row : rows) {
            String[] rowTemp = row[0].replaceAll("[\"]", "").split(";");
            try {
                Produto produto = new Produto();
                LinhaCategoria linhaCategoria;
                produto.setCodProduto(rowTemp[0]);
                Optional<Produto> produtoOptional = this.iProdutoRepository.findByCodProduto(produto.getCodProduto());
                if (produtoOptional.isPresent()) {

                } else {
                    produto.setNomeProduto(rowTemp[1]);
                    produto.setPrecoProduto(Double.parseDouble(rowTemp[2].replace("R$", "").replace(",", ".")));
                    produto.setUnidadeCaixaProduto(Long.parseLong(rowTemp[3]));
                    produto.setPesoUnidade(Double.parseDouble(rowTemp[4]));
                    produto.setMedidaPeso(rowTemp[5]);
                    produto.setValidade(LocalDateTime.from(formatter.parse(rowTemp[6])));
                    linhaCategoria = linhaCategoriaService.findByCodLinhaCategoria((rowTemp[7]));
                    rowTemp[7] = String.valueOf(linhaCategoria.getId());
                    linhaCategoria.setId(Long.valueOf(rowTemp[7]));
                    Optional<LinhaCategoria> linhaCategoriaOptional = Optional.ofNullable(this.linhaCategoriaService.findByCodLinhaCategoria(linhaCategoria.getCodLinhaCategoria()));
                    if (linhaCategoriaOptional.isPresent()) {
                        linhaCategoria.setId(linhaCategoria.getId());
                        linhaCategoria.setNomeLinha(rowTemp[8]);
                        produto.setLinhaCategoria(linhaCategoria);
                        produtos.add(produto);
                        return iProdutoRepository.saveAll(produtos);
                    } else {
                        throw new IllegalArgumentException("esta linha de produtos ja existe");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return produtos;
    }

    public List<Produto> saveImportById(Long id, MultipartFile file) throws Exception {
        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        List<String[]> rows = csvReader.readAll();
        List<Produto> produtos = new ArrayList<>();
        String codigo = "";
        for (String[] row : rows) {
            String[] rowTemp = row[0].replaceAll("[\"]", "").split(";");
            try {
                Produto produto = new Produto();
                Fornecedor fornecedor;
                LinhaCategoria linhaCategoria = new LinhaCategoria();
                CategoriaProduto categoriaProduto = new CategoriaProduto();
                Optional<Produto> produtoOptional = Optional.ofNullable(findByCodProduto(rowTemp[0]));
                if (produtoOptional.isPresent()) {
                    produto = findByCodProduto(rowTemp[0]);
                    produto.setCodProduto(rowTemp[0]);
                    produto.setId(produto.getId());
                    produto.setNomeProduto(rowTemp[1]);
                    produto.setPrecoProduto(Double.parseDouble(rowTemp[2].replace("R$", "").replace(",", ".")));
                    produto.setUnidadeCaixaProduto(Long.parseLong(rowTemp[3]));
                    produto.setPesoUnidade(Double.parseDouble(rowTemp[4]));
                    produto.setMedidaPeso(rowTemp[5]);
                    produto.setValidade(LocalDateTime.from(formatter.parse(rowTemp[6])));
                    produto.setLinhaCategoria(linhaCategoriaService.findByCodLinhaCategoria(rowTemp[7]));
                    update(ProdutoDTO.of(produto), produto.getId());
                } else {
                    produto.setCodProduto(rowTemp[0]);
                    produto.setNomeProduto(rowTemp[1]);
                    produto.setPrecoProduto(Double.parseDouble(rowTemp[2].replace("R$", "").replace(",", ".")));
                    produto.setUnidadeCaixaProduto(Long.parseLong(rowTemp[3]));
                    produto.setPesoUnidade(Double.parseDouble(rowTemp[4]));
                    produto.setMedidaPeso(rowTemp[5]);
                    produto.setValidade(LocalDateTime.from(formatter.parse(rowTemp[6])));
                    Optional<CategoriaProduto> categoriaProdutoOptional = Optional.ofNullable(this.categoriaProdutoService.findByCodCategoria(rowTemp[9]));
                    if (categoriaProdutoOptional.isPresent()) {
                        categoriaProduto = categoriaProdutoService.findByCodCategoria(rowTemp[9]);
                        while (categoriaProduto.getCodCategoria().length() < 10) {
                            codigo += "0";
                            produto.setCodProduto("CAT" + codigo + categoriaProduto.getCodCategoria().toUpperCase());
                        }
                        categoriaProduto.setId(categoriaProduto.getId());
                        categoriaProduto.setNomeCategoria(rowTemp[10]);
                    } else {
                        Optional<Fornecedor> fornecedorOptional = Optional.ofNullable(this.fornecedorService.findFornecedorById(id));
                        fornecedor = fornecedorOptional.get();
                        categoriaProduto.setCodCategoria(rowTemp[7]);
                        categoriaProduto.setNomeCategoria(rowTemp[10]);
                        categoriaProduto.setFornecedor(fornecedor);
                        categoriaProdutoService.save(CategoriaProdutoDTO.of(categoriaProduto));
                    }
                    Optional<LinhaCategoria> linhaCategoriaOptional = Optional.ofNullable(this.linhaCategoriaService.findByCodLinhaCategoria(rowTemp[7]));
                    if (linhaCategoriaOptional.isPresent()) {
                        linhaCategoria = linhaCategoriaService.findByCodLinhaCategoria((rowTemp[7]));
                        linhaCategoria.setId(linhaCategoria.getId());
                        linhaCategoria.setCategoriaProduto(categoriaProduto);
                        linhaCategoriaService.update(LinhaCategoriaDTO.of(linhaCategoria), linhaCategoria.getId());
                        return saveAll(produtos);
                    } else {
                        linhaCategoria.setCodLinhaCategoria(rowTemp[7]);
                        linhaCategoria.setNomeLinha(rowTemp[8]);
                        linhaCategoria.setCategoriaProduto(categoriaProduto);
                        linhaCategoriaService.save(LinhaCategoriaDTO.of(linhaCategoria));
                        produto.setLinhaCategoria(linhaCategoria);
                        produtos.add(produto);
                        return saveAll(produtos);
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return produtos;
    }
}

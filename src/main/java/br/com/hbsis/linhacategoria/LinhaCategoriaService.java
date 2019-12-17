package br.com.hbsis.linhacategoria;

import br.com.hbsis.categoriaproduto.CategoriaProduto;
import br.com.hbsis.categoriaproduto.CategoriaProdutoService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LinhaCategoriaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaProdutoService.class);

    private final ILinhaCategoriaRepository iLinhaCategoriaRepository;
    private final CategoriaProdutoService categoriaProdutoService;

    public LinhaCategoriaService(ILinhaCategoriaRepository iLinhaCategoriaRepository, CategoriaProdutoService categoriaProdutoService) {
        this.iLinhaCategoriaRepository = iLinhaCategoriaRepository;
        this.categoriaProdutoService = categoriaProdutoService;
    }

    public List<LinhaCategoria> findAll() {
        List<LinhaCategoria> linhaCategorias = iLinhaCategoriaRepository.findAll();
        return linhaCategorias;
    }

    public List<LinhaCategoria> saveAll(List<LinhaCategoria> linhaCategorias) {
        return iLinhaCategoriaRepository.saveAll(linhaCategorias);
    }


    public LinhaCategoriaDTO save(LinhaCategoriaDTO linhaCategoriaDTO) {
        CategoriaProduto findCategoriaProdutoid = categoriaProdutoService.findCategoriaProdutoById(linhaCategoriaDTO.getIdCategoriaProduto());

        this.validate(linhaCategoriaDTO);

        LOGGER.info("Salvando Linha da Categoria");
        LOGGER.debug("Linha da Categoria: {}", linhaCategoriaDTO);

        String codigo = "";
        LinhaCategoria linhaCategoria = new LinhaCategoria();
        linhaCategoria.setCodLinhaCategoria(linhaCategoriaDTO.getCodLinhaCategoria().toUpperCase());
        if (linhaCategoria.getCodLinhaCategoria().equals(null)) {
            throw new IllegalArgumentException("Codigo de linha nao pode ser nulo");
        } else
            while (linhaCategoria.getCodLinhaCategoria().length() < 10) {
                codigo += "0";
                linhaCategoria.setCodLinhaCategoria(codigo + linhaCategoriaDTO.getCodLinhaCategoria().toUpperCase());
            }
        if (linhaCategoria.getCodLinhaCategoria().length() > 10) {
            throw new IllegalArgumentException("codigo nao pode ter mais que dez digitos otario");
        } else {
            linhaCategoria.setCategoriaProduto(findCategoriaProdutoid);
            linhaCategoria.setNomeLinha(linhaCategoriaDTO.getNomeLinha());
            linhaCategoria = this.iLinhaCategoriaRepository.save(linhaCategoria);
        }
        return linhaCategoriaDTO.of(linhaCategoria);
    }

    private void validate(LinhaCategoriaDTO linhaCategoriaDTO) {
        LOGGER.info("Validando Linha da Categoria");
        if (linhaCategoriaDTO == null) {
            throw new IllegalArgumentException("LinhaCategoriaDTO não deve ser nulo");
        }
        if (StringUtils.isEmpty(linhaCategoriaDTO.getCodLinhaCategoria())) {
            throw new IllegalArgumentException("Código da linha não deve ser nula/vazia");
        }
        if (StringUtils.isEmpty(linhaCategoriaDTO.getNomeLinha())) {
            throw new IllegalArgumentException("Nome da linha não deve ser nula/vazia");
        }
    }

    public LinhaCategoriaDTO findById(Long id) {
        Optional<LinhaCategoria> linhaCategoriaOptional = this.iLinhaCategoriaRepository.findById(id);
        if (linhaCategoriaOptional.isPresent()) {
            return LinhaCategoriaDTO.of(linhaCategoriaOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public LinhaCategoria findByCodLinhaCategoria(String codLinhaCategoria) {
        Optional<LinhaCategoria> linhaCategoriaOptional = this.iLinhaCategoriaRepository.findByCodLinhaCategoria(codLinhaCategoria);
        if (linhaCategoriaOptional.isPresent()) {
            return linhaCategoriaOptional.get();
        }
        throw new IllegalArgumentException(String.format("Codigo %s não existe", codLinhaCategoria));
    }


    public LinhaCategoria findLinhaCategoriaById(long id) {
        Optional<LinhaCategoria> linhaCategoriaOptional = this.iLinhaCategoriaRepository.findById(id);
        if (linhaCategoriaOptional.isPresent()) {
            return linhaCategoriaOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public LinhaCategoriaDTO update(LinhaCategoriaDTO linhaCategoriaDTO, Long id) {
        Optional<LinhaCategoria> linhaExistenteOptional = this.iLinhaCategoriaRepository.findById(id);
        CategoriaProduto findCategoriaProdutoid = categoriaProdutoService.findCategoriaProdutoById(linhaCategoriaDTO.getIdCategoriaProduto());
        if (linhaExistenteOptional.isPresent()) {
            LinhaCategoria linhaProdutoExistente = linhaExistenteOptional.get();

            LOGGER.info("Atualizando Linha da Categoria... id: [{}]", linhaProdutoExistente.getId());
            LOGGER.debug("Payload: {}", linhaCategoriaDTO);
            LOGGER.debug("Linha da Categoria Existente: {}", linhaProdutoExistente);
            String codigo = "";
            linhaProdutoExistente.setCodLinhaCategoria(linhaCategoriaDTO.getCodLinhaCategoria());
            if (linhaProdutoExistente.getCodLinhaCategoria() == null) {
                throw new IllegalArgumentException("Codigo de linha nao pode ser nulo");
            } else
                while (linhaProdutoExistente.getCodLinhaCategoria().length() < 10) {
                    codigo += "0";
                    linhaProdutoExistente.setCodLinhaCategoria(codigo + linhaCategoriaDTO.getCodLinhaCategoria().toUpperCase());
                }
            if (linhaProdutoExistente.getCodLinhaCategoria().length() > 10) {
                throw new IllegalArgumentException("codigo nao pode ter mais que dez digitos otario");
            } else {
                linhaProdutoExistente.setCategoriaProduto(findCategoriaProdutoid);
                linhaProdutoExistente.setNomeLinha(linhaCategoriaDTO.getNomeLinha());
                linhaProdutoExistente = this.iLinhaCategoriaRepository.save(linhaProdutoExistente);
            }
            return LinhaCategoriaDTO.of(linhaProdutoExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Linha da Categoria de ID: [{}]", id);
        this.iLinhaCategoriaRepository.deleteById(id);
    }

    public Optional<LinhaCategoria> BDuse(String codLinhaCat) {
        Optional<LinhaCategoria> findByCodLinhaCategoria = this.iLinhaCategoriaRepository.findByCodLinhaCategoria(codLinhaCat);
        return findByCodLinhaCategoria;
    }

    public void exportCSV(HttpServletResponse response) throws IOException {
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
        for (LinhaCategoria linhaCategoria : iLinhaCategoriaRepository.findAll()) {
            Writer.writeNext(new String[]
                    {linhaCategoria.getCodLinhaCategoria(), linhaCategoria.getNomeLinha(),
                            linhaCategoria.getCategoriaProduto().getCodCategoria(), linhaCategoria.getCategoriaProduto().getNomeCategoria()});
        }
    }

    public List<LinhaCategoria> importCSV(MultipartFile file) throws Exception {
        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).build();
        List<String[]> rows = csvReader.readAll();
        List<LinhaCategoria> linhaCategorias = new ArrayList<>();
        for (String[] row : rows) {
            String[] rowTemp = row[0].replaceAll("[-\"./]", "").split(";");
            try {
                LinhaCategoria linhaCategoria = new LinhaCategoria();
                CategoriaProduto categoriaProduto;
                linhaCategoria.setCodLinhaCategoria((rowTemp[0]));
                Optional<LinhaCategoria> linhaCategoriaOptional = this.iLinhaCategoriaRepository.findByCodLinhaCategoria(linhaCategoria.getCodLinhaCategoria());
                if (linhaCategoriaOptional.isPresent()) {

                } else {
                    linhaCategoria.setNomeLinha(rowTemp[1]);
                    categoriaProduto = categoriaProdutoService.findByCodCategoria((rowTemp[2]));
                    categoriaProduto.setId(categoriaProduto.getId());
                    linhaCategoria.setCategoriaProduto(categoriaProduto);
                    linhaCategorias.add(linhaCategoria);
                    return iLinhaCategoriaRepository.saveAll(linhaCategorias);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return linhaCategorias;
    }
}

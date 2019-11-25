package br.com.hbsis.fornecedor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorRest.class);

    private final FornecedorService fornecedorService;

    @Autowired
    public FornecedorRest(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    public FornecedorDTO save(@RequestBody FornecedorDTO fornecedorDTO) {
        LOGGER.info("Recebendo solicitação de persistência de Fornecedor...");
        LOGGER.debug("Payaload: {}", fornecedorDTO);

        return this.fornecedorService.save(fornecedorDTO);
    }

    @GetMapping("/{id_fornecedor}")
    public FornecedorDTO find(@PathVariable("id_fornecedor") Long id_fornecedor) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id_fornecedor);

        return this.fornecedorService.findById(id_fornecedor);
    }

    @PutMapping("/{id_fornecedor}")
    public FornecedorDTO udpate(@PathVariable("id_fornecedor") Long id_fornecedor, @RequestBody FornecedorDTO fornecedorDTO) {
        LOGGER.info("Recebendo Update para Fornecedor de ID: {}", id_fornecedor);
        LOGGER.debug("Payload: {}", fornecedorDTO);

        return this.fornecedorService.update(fornecedorDTO, id_fornecedor);
    }

    @DeleteMapping("/{id_fornecedor}")
    public void delete(@PathVariable("id_fornecedor") Long id_fornecedor) {
        LOGGER.info("Recebendo Delete para Fornecedor de ID: {}", id_fornecedor);

        this.fornecedorService.delete(id_fornecedor);
    }
}

package br.com.hbsis.vendafornecedor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vendaFornecedores")
public class VendaFornecedorRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(VendaFornecedorRest.class);
    private final VendaFornecedorService vendaFornecedorService;

    @Autowired
    public VendaFornecedorRest(VendaFornecedorService vendaFornecedorService) {
        this.vendaFornecedorService = vendaFornecedorService;
    }
    @PostMapping
    public VendaFornecedorDTO save(@RequestBody VendaFornecedorDTO vendaFornecedorDTO) {
        LOGGER.info("Recebendo solicitação de persistência de Produto...");
        LOGGER.debug("Payaload: {}", vendaFornecedorDTO);
        return this.vendaFornecedorService.save(vendaFornecedorDTO);
    }
    @GetMapping("/todos")
    public List<VendaFornecedor> findVendaFornecedor() {
        List<VendaFornecedor> vendaFornecedors = vendaFornecedorService.findAll();
        return vendaFornecedors;
    }
    @GetMapping("/{id}")
    public VendaFornecedorDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by ID... id: [{}]", id);
        return this.vendaFornecedorService.findById(id);
    }
    @PutMapping("/{id}")
    public VendaFornecedorDTO udpate(@PathVariable("id") Long id, @RequestBody VendaFornecedorDTO vendaFornecedorDTO) {
        LOGGER.info("Recebendo Update para Produto de ID: {}", id);
        LOGGER.debug("Payload: {}", vendaFornecedorDTO);
        return this.vendaFornecedorService.update(vendaFornecedorDTO, id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para Categoria do Produto de ID: {}", id);
        this.vendaFornecedorService.delete(id);
    }
}

package br.com.hbsis.fornecedor;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class FornecedorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorService.class);

    private final IFornecedorRepository iFornecedorRepository;

    public FornecedorService(IFornecedorRepository iFornecedorRepository) {
                            this.iFornecedorRepository = iFornecedorRepository;
    }
    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {

        this.validate(fornecedorDTO);

        LOGGER.info("Salvando fornecedor");
        LOGGER.debug("Fornecedor: {}", fornecedorDTO);

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setRazao_Social(fornecedorDTO.getRazao_social());
        fornecedor.setCNPJ(fornecedorDTO.getCNPJ());
        fornecedor.setNome_Fantasia(fornecedorDTO.getNome_fantasia());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setTelefone_contato(fornecedorDTO.getTelefone_contato());
        fornecedor.setEmail_contato(fornecedorDTO.getEmail_contato());

        fornecedor = this.iFornecedorRepository.save(fornecedor);

        return FornecedorDTO.of(fornecedor);
    }

    private void validate(FornecedorDTO fornecedorDTO) {
        LOGGER.info("Validando Fornecedor");

        if (fornecedorDTO == null) {
            throw new IllegalArgumentException("FornecedorDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(fornecedorDTO.getRazao_social())) {
            throw new IllegalArgumentException("Razão Social não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(fornecedorDTO.getCNPJ())) {
            throw new IllegalArgumentException("CNPJ não deve ser nulo/vazio");
        }
    }

    public FornecedorDTO findById(Long id_fornecedor) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id_fornecedor);

        if (fornecedorOptional.isPresent()) {
            return FornecedorDTO.of(fornecedorOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id_fornecedor));
    }


    public FornecedorDTO update(FornecedorDTO fornecedorDTO, Long id_fornecedor) {
        Optional<Fornecedor> fornecedorExistenteOptional = this.iFornecedorRepository.findById(id_fornecedor);

        if (fornecedorExistenteOptional.isPresent()) {
            Fornecedor fornecedorExistente = fornecedorExistenteOptional.get();

            LOGGER.info("Atualizando fornecedor... id: [{}]", fornecedorExistente.getId_fornecedor());
            LOGGER.debug("Payload: {}", fornecedorDTO);
            LOGGER.debug("Fornecedor Existente: {}", fornecedorExistente);

            fornecedorExistente.setRazao_Social(fornecedorDTO.getRazao_social());
            fornecedorExistente.setCNPJ(fornecedorDTO.getCNPJ());
            fornecedorExistente.setNome_Fantasia(fornecedorDTO.getNome_fantasia());
            fornecedorExistente.setEndereco(fornecedorDTO.getEndereco());
            fornecedorExistente.setTelefone_contato(fornecedorDTO.getTelefone_contato());
            fornecedorExistente.setEmail_contato(fornecedorDTO.getEmail_contato());

            fornecedorExistente = this.iFornecedorRepository.save(fornecedorExistente);

            return FornecedorDTO.of(fornecedorExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id_fornecedor));

    }

    public void delete(Long id_fornecedor) {
        LOGGER.info("Executando delete para Fornecedor de ID: [{}]", id_fornecedor);

        this.iFornecedorRepository.deleteById(id_fornecedor);
    }
}

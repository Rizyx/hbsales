package br.com.hbsis.funcionario;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioService.class);
    private final IFuncionarioRepository iFuncionarioRepository;

    public FuncionarioService(IFuncionarioRepository iFuncionarioRepository) {
        this.iFuncionarioRepository = iFuncionarioRepository;
    }

    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO) {
        this.validate(funcionarioDTO);

        LOGGER.info("Salvando fornecedor");
        LOGGER.debug("Fornecedor: {}", funcionarioDTO);

        Funcionario funcionario = new Funcionario();
        funcionario.setNomeFuncionario(funcionarioDTO.getNomeFuncionario());
        funcionario.setEmailFuncionario(funcionarioDTO.getEmailFuncionario());
        funcionario = this.iFuncionarioRepository.save(funcionario);

        return FuncionarioDTO.of(funcionario);
    }

    private void validate(FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Validando Funcionario");
        if (funcionarioDTO == null) {
            throw new IllegalArgumentException("FuncionarioDTO não deve ser nulo");
        }
        if (StringUtils.isEmpty(funcionarioDTO.getNomeFuncionario())) {
            throw new IllegalArgumentException("Nome do Funcionario não deve ser nula/vazia");
        }
    }

    public FuncionarioDTO findById(Long id) {
        Optional<Funcionario> funcionarioOptional = this.iFuncionarioRepository.findById(id);
        if (funcionarioOptional.isPresent()) {
            return FuncionarioDTO.of(funcionarioOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }
    public FuncionarioDTO update(FuncionarioDTO funcionarioDTO, Long id) {
        Optional<Funcionario> funcionarioExistenteOptional = this.iFuncionarioRepository.findById(id);
        if (funcionarioExistenteOptional.isPresent()) {
            Funcionario funcionarioExistente = funcionarioExistenteOptional.get();

            LOGGER.info("Atualizando fornecedor... id: [{}]", funcionarioExistente.getId());
            LOGGER.debug("Payload: {}", funcionarioDTO);
            LOGGER.debug("Fornecedor Existente: {}", funcionarioExistente);

            funcionarioExistente.setNomeFuncionario(funcionarioDTO.getNomeFuncionario());
            funcionarioExistente.setEmailFuncionario(funcionarioDTO.getEmailFuncionario());
            funcionarioExistente = this.iFuncionarioRepository.save(funcionarioExistente);

            return FuncionarioDTO.of(funcionarioExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public List<Funcionario> findAll() {
        List<Funcionario> funcionarios = iFuncionarioRepository.findAll();
        return funcionarios;
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Fornecedor de ID: [{}]", id);
        this.iFuncionarioRepository.deleteById(id);
    }

}

package br.com.hbsis.vendafornecedor;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VendaFornecedorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VendaFornecedorService.class);
    private final IVendaFornecedorRepository iVendaFornecedorRepository;
    private final FornecedorService fornecedorService;

    public VendaFornecedorService(IVendaFornecedorRepository iVendaFornecedorRepository, FornecedorService fornecedorService) {
        this.iVendaFornecedorRepository = iVendaFornecedorRepository;
        this.fornecedorService = fornecedorService;
    }

    public List<VendaFornecedor> findAll() {
        List<VendaFornecedor> categoriaProdutos = iVendaFornecedorRepository.findAll();
        return categoriaProdutos;
    }

    public VendaFornecedorDTO save(VendaFornecedorDTO vendaFornecedorDTO) {
        Fornecedor findFornecedorid = fornecedorService.findFornecedorById(vendaFornecedorDTO.getIdFornecedor());
        this.validate(vendaFornecedorDTO);

        LOGGER.info("Salvando Categoria do produto");
        LOGGER.debug("Categoria do Produto: {}", vendaFornecedorDTO);
        VendaFornecedor vendaFornecedor = new VendaFornecedor();
        vendaFornecedor.setDataInicio(vendaFornecedorDTO.getDataInicio());
        vendaFornecedor.setDataFinal(vendaFornecedorDTO.getDataFinal());
        vendaFornecedor.setDiaRetirada(vendaFornecedorDTO.getDiaRetirada());
        vendaFornecedor.setFornecedor(findFornecedorid);
        vendaFornecedor.setDescricao(vendaFornecedorDTO.getDescricao());
        for (VendaFornecedor vendaFornecedores : iVendaFornecedorRepository.findAll()) {
            if (vendaFornecedor.getDataInicio().isBefore(vendaFornecedores.getDataFinal())) {
                if (vendaFornecedor.getDataFinal().isAfter(vendaFornecedores.getDataInicio())) {
                    throw new IllegalArgumentException("data invalida");
                }
            }
            if (LocalDateTime.now().isAfter(vendaFornecedores.getDataInicio())) {
                throw new IllegalArgumentException("dia ja passou");
            } else {
                if (vendaFornecedor.getDataInicio().isAfter(vendaFornecedor.getDataFinal())) {
                    throw new IllegalArgumentException("Data de inicio apos data final");
                } else if (vendaFornecedor.getDiaRetirada().isBefore(vendaFornecedor.getDataFinal())) {
                    throw new IllegalArgumentException("dia de retirada invalido");
                } else {
                    vendaFornecedor = this.iVendaFornecedorRepository.save(vendaFornecedor);
                }
                return VendaFornecedorDTO.of(vendaFornecedor);
            }
        }
        return vendaFornecedorDTO;
    }

    private void validate(VendaFornecedorDTO vendaFornecedorDTO) {
        LOGGER.info("Validando Produto");
        if (vendaFornecedorDTO == null) {
            throw new IllegalArgumentException("linhaCategoriaDTO não deve ser nulo");
        }
        if (StringUtils.isEmpty(String.valueOf(vendaFornecedorDTO.getDataInicio()))) {
            throw new IllegalArgumentException("Codigo do produto não deve ser nula/vazia");
        }
        if (StringUtils.isEmpty(String.valueOf(vendaFornecedorDTO.getDataFinal()))) {
            throw new IllegalArgumentException("Nome do produto não deve ser nula/vazia");
        }
        if (StringUtils.isEmpty(String.valueOf(vendaFornecedorDTO.getDiaRetirada()))) {
            throw new IllegalArgumentException("Preço do produto não deve ser nula/vazia");
        }
    }

    public VendaFornecedorDTO findById(Long id) {
        Optional<VendaFornecedor> produtoOptional = this.iVendaFornecedorRepository.findById(id);
        if (produtoOptional.isPresent()) {
            return VendaFornecedorDTO.of(produtoOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public VendaFornecedor findVendaFornecedorById(long id) {
        Optional<VendaFornecedor> vendaFornecedorOptional = this.iVendaFornecedorRepository.findById(id);
        if (vendaFornecedorOptional.isPresent()) {
            return vendaFornecedorOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public VendaFornecedorDTO update(VendaFornecedorDTO vendaFornecedorDTO, Long id) {
        Optional<VendaFornecedor> vendaExistenteOptional = this.iVendaFornecedorRepository.findById(id);
        Fornecedor findFornecedorid = fornecedorService.findFornecedorById(vendaFornecedorDTO.getIdFornecedor());
        if (vendaExistenteOptional.isPresent()) {
            VendaFornecedor vendaFornecedorExistente = vendaExistenteOptional.get();

            LOGGER.info("Atualizando Categoria do produto... id: [{}]", vendaFornecedorExistente.getId());
            LOGGER.debug("Payload: {}", vendaFornecedorDTO);
            LOGGER.debug("Categoria do Produto Existente: {}", vendaFornecedorExistente);
            vendaFornecedorExistente.setDataInicio(vendaFornecedorDTO.getDataInicio());
            vendaFornecedorExistente.setDataFinal(vendaFornecedorDTO.getDataFinal());
            vendaFornecedorExistente.setDiaRetirada(vendaFornecedorDTO.getDiaRetirada());
            vendaFornecedorExistente.setFornecedor(findFornecedorid);
            vendaFornecedorExistente.setDescricao(vendaFornecedorDTO.getDescricao());
            if (LocalDateTime.now().isAfter(vendaFornecedorExistente.getDataFinal())) {
                throw new IllegalArgumentException("Periodo de vendas fechado");
            } else {
                for (VendaFornecedor vendaFornecedores : iVendaFornecedorRepository.findAll()) {
                    if (vendaFornecedorExistente.getDataInicio().isBefore(vendaFornecedores.getDataFinal())) {
                        if (vendaFornecedorExistente.getDataFinal().isAfter(vendaFornecedores.getDataInicio())) {
                            throw new IllegalArgumentException("data invalida");
                        }
                    } else {
                        if (LocalDateTime.now().isAfter(vendaFornecedorExistente.getDataInicio())) {
                            throw new IllegalArgumentException("data nao pode ser alterada");
                        } else {
                            if (vendaFornecedorExistente.getDataInicio().isAfter(vendaFornecedorExistente.getDataFinal())) {
                                System.out.println("data inicial depois da final");
                            }
                            if (vendaFornecedorExistente.getDiaRetirada().isBefore(vendaFornecedorExistente.getDataFinal())) {
                                System.out.println("dia de retirada invalido");
                            } else {
                                vendaFornecedorExistente = this.iVendaFornecedorRepository.save(vendaFornecedorExistente);
                                return VendaFornecedorDTO.of(vendaFornecedorExistente);
                            }
                        }
                    }
                }
            }
            throw new IllegalArgumentException(String.format("ID %s não existe", id));
        }
        return vendaFornecedorDTO;
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Produto de ID: [{}]", id);
        this.iVendaFornecedorRepository.deleteById(id);
    }
}

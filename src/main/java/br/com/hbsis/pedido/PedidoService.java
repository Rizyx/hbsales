package br.com.hbsis.pedido;

import br.com.hbsis.produto.Produto;
import br.com.hbsis.produto.ProdutoService;
import br.com.hbsis.vendafornecedor.VendaFornecedor;
import br.com.hbsis.vendafornecedor.VendaFornecedorService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class PedidoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);
    private final IPedidoRepository iPedidoRepository;
    private final ProdutoService produtoService;
    private final VendaFornecedorService vendaFornecedorService;

    public PedidoService(IPedidoRepository iPedidoRepository,
                         ProdutoService produtoService, VendaFornecedorService vendaFornecedorService) {
        this.iPedidoRepository = iPedidoRepository;
        this.produtoService = produtoService;
        this.vendaFornecedorService = vendaFornecedorService;
    }

    public List<Pedido> findAll() {
        List<Pedido> produtos = iPedidoRepository.findAll();
        return produtos;
    }

    public PedidoDTO save(PedidoDTO pedidoDTO) {
        Produto findProdutoId = produtoService.findProdutoById(pedidoDTO.getProduto());
        VendaFornecedor findVendaFornecedorById = vendaFornecedorService.findVendaFornecedorById(pedidoDTO.getVendaFornecedor());
        String status;
        this.validate(pedidoDTO);

        LOGGER.info("Salvando produto");
        LOGGER.debug("Produto: {}", pedidoDTO);

        Pedido pedido = new Pedido();
        Scanner scanner =  new Scanner(System.in);
        pedido.setNomeFuncionario(pedidoDTO.getNomeFuncionario());
        pedido.setProduto(findProdutoId);
        pedido.setQuantidadeProduto(pedidoDTO.getQuantidadeProduto());
        pedido.setStatusPedido(pedidoDTO.getStatusPedido());
        pedido.setVendaFornecedor(findVendaFornecedorById);
        pedido.setDataRetirada(pedidoDTO.getDataRetirada());
        if(LocalDateTime.now().isAfter(findVendaFornecedorById.getDataInicio()) &&
                LocalDateTime.now().isBefore(findVendaFornecedorById.getDataFinal())){
            status = scanner.next();
            if(status.equalsIgnoreCase("Sim")){
                pedido = this.iPedidoRepository.save(pedido);
            }else{
                throw new IllegalArgumentException("pedido nao realizado");
            }
        }else{
            throw new IllegalArgumentException("este pedido nao pode mais ser realizado");
        }
        scanner.close();
        return pedidoDTO.of(pedido);
    }

    private void validate(PedidoDTO pedidoDTO) {
        LOGGER.info("Validando Produto");
        if (pedidoDTO == null) {
            throw new IllegalArgumentException("linhaCategoriaDTO não deve ser nulo");
        }
        if (StringUtils.isEmpty(pedidoDTO.getNomeFuncionario())) {
            throw new IllegalArgumentException("Nome do Funcionario não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getQuantidadeProduto()))) {
            throw new IllegalArgumentException("Quantidade do pedido não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getStatusPedido()))) {
            throw new IllegalArgumentException("Status do pedido não deve ser nulo/vazio");
        }
    }

    public PedidoDTO findById(Long id) {
        Optional<Pedido> pedidoOptional = this.iPedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            return PedidoDTO.of(pedidoOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public PedidoDTO update(PedidoDTO pedidoDTO, Long id) {
        Optional<Pedido> pedidoExistenteOptional = this.iPedidoRepository.findById(id);
        Produto findProdutoById = produtoService.findProdutoById(pedidoDTO.getProduto());
        VendaFornecedor findVendaFornecedorById = vendaFornecedorService.findVendaFornecedorById(pedidoDTO.getVendaFornecedor());
        if (pedidoExistenteOptional.isPresent()) {
            Pedido pedidoExistente = pedidoExistenteOptional.get();
            LOGGER.info("Atualizando produto... id: [{}]", pedidoExistente.getId());
            LOGGER.debug("Payload: {}", pedidoDTO);
            LOGGER.debug("Produto Existente: {}", pedidoExistente);

            pedidoExistente.setNomeFuncionario(pedidoDTO.getNomeFuncionario());
            pedidoExistente.setProduto(findProdutoById);
            pedidoExistente.setQuantidadeProduto(pedidoDTO.getQuantidadeProduto());
            pedidoExistente.setStatusPedido(pedidoDTO.getStatusPedido());
            pedidoExistente.setVendaFornecedor(findVendaFornecedorById);
            pedidoExistente.setDataRetirada(pedidoDTO.getDataRetirada());
            if(LocalDateTime.now().isAfter(findVendaFornecedorById.getDataInicio()) &&
                    LocalDateTime.now().isBefore(findVendaFornecedorById.getDataFinal())){
                if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getDataRetirada()))) {
                    pedidoExistente = this.iPedidoRepository.save(pedidoExistente);
                    return PedidoDTO.of(pedidoExistente);
                }else{
                    throw new IllegalArgumentException("Pedido ja foi retirado");
                }
                }else{
                    throw new IllegalArgumentException("Pedido fora do periodo de vendas");
                }
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Produto de ID: [{}]", id);
        this.iPedidoRepository.deleteById(id);
        PedidoDTO pedidoDTO = findById(id);
        VendaFornecedor findVendaFornecedorById = vendaFornecedorService.findVendaFornecedorById(pedidoDTO.getVendaFornecedor());
        if(LocalDateTime.now().isAfter(findVendaFornecedorById.getDataInicio()) &&
                LocalDateTime.now().isBefore(findVendaFornecedorById.getDataFinal())){
            if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getDataRetirada()))) {
            }else{
                throw new IllegalArgumentException("Pedido ja foi retirado");
            }
        }else{
            throw new IllegalArgumentException("Pedido fora do periodo de vendas");
        }
    }
}




package br.com.hbsis.fornecedor;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class VendaFornecedorServiceTest {


    @Mock
    private IFornecedorRepository iFornecedorRepository;

    @Captor
    private ArgumentCaptor<Fornecedor> argumentCaptor;

    @InjectMocks
    private FornecedorService fornecedorService;

    @Test
    public void save() {
        FornecedorDTO fornecedorDTO = new FornecedorDTO(1,"minha.RazaoSocial","meu.cnpj","Nome.NomeFantasia");

        Fornecedor fornecedorMock = Mockito.mock(Fornecedor.class);

        when(fornecedorMock.getRazaoSocial()).thenReturn(fornecedorDTO.getRazaoSocial());
        when(fornecedorMock.getCnpj()).thenReturn(fornecedorDTO.getCnpj());
        when(fornecedorMock.getNomeFantasia()).thenReturn(fornecedorDTO.getNomeFantasia());

        when(this.iFornecedorRepository.save(any())).thenReturn(fornecedorMock);

        this.fornecedorService.save(fornecedorDTO);

        verify(this.iFornecedorRepository, times(1)).save(this.argumentCaptor.capture());
        Fornecedor createdFornecedor = argumentCaptor.getValue();

        assertTrue(StringUtils.isNoneEmpty(createdFornecedor.getRazaoSocial()), "Razao social não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdFornecedor.getCnpj()), "cnpj não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdFornecedor.getNomeFantasia()), "nome fantasia não deve ser nulo");

    }

    @Test
    public void findById() {
        FornecedorDTO fornecedorDTO = new FornecedorDTO(1,"minha.RazaoSocial","meu.cnpj","Nome.NomeFantasia");

        Fornecedor fornecedorMock = Mockito.mock(Fornecedor.class);
        when(fornecedorMock.getId()).thenReturn(fornecedorDTO.getIdFornecedor());
        when(this.iFornecedorRepository.findById(any())).thenReturn(java.util.Optional.of(fornecedorMock));
        this.fornecedorService.save(fornecedorDTO);
        verify(this.iFornecedorRepository, times(1)).save(this.argumentCaptor.capture());
        Fornecedor createdFornecedor = argumentCaptor.getValue();
        assertTrue(StringUtils.isNoneEmpty(String.valueOf(createdFornecedor.getId())), "id não deve ser nulo");
    }

    @Test
    public void findFornecedorById() {
    }

    @Test
    public void update() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void delete() {
    }
}
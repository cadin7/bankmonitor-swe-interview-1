package bankmonitor.controller;

import bankmonitor.model.api.TransactionDto;
import bankmonitor.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                        new TransactionController(transactionService))
                .build();
    }

    @Test
    void getAllTransactionsTest() throws Exception {
        final var jsonData = """
                {
                    "amount": 1
                }
                """;
        final var transactionDto = TransactionDto.builder()
                .data(jsonData)
                .build();

        when(transactionService.findAll())
                .thenReturn(List.of(transactionDto));

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void createTransactionTest() throws Exception {
        final var jsonData = """
                {
                    "amount": 100
                }
                """;
        final var transactionDto = TransactionDto.builder()
                .data(jsonData)
                .build();

        when(transactionService.createTransaction(anyString()))
                .thenReturn(transactionDto);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isOk());
    }

    @Test
    void updateTransactionTest() throws Exception {
        final var id = 1L;
        final var updateData = """
                {
                    "amount": 100
                }
                """;
        final var transactionDto = TransactionDto.builder()
                .data(updateData)
                .build();
        when(transactionService.updateTransaction(eq(id), anyString()))
                .thenReturn(transactionDto);

        mockMvc.perform(put("/transactions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateData))
                .andExpect(status().isOk());
    }
}

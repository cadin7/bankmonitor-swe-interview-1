package bankmonitor.service;

import bankmonitor.exceptions.TransactionNotFoundException;
import bankmonitor.model.entity.Transaction;
import bankmonitor.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    private TransactionService service;

    @BeforeEach
    void setUp() {
        service = new TransactionService(repository);
    }

    @Test
    @DisplayName("WHEN findAll called THEN the result is a TransactionDto list")
    void findAll() {
        final var jsonData = """
                {
                    "reference": "foo",
                    "amount": 300
                }
                """;
        final var transactions = List.of(
                new Transaction(jsonData),
                new Transaction(jsonData));

        when(repository.findAll())
                .thenReturn(transactions);

        final var result = service.findAll();

        assertNotNull(result);
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("WHEN createTransaction called THEN the result is the created transaction")
    void createTransaction() {
        final var jsonData = """
                {
                    "reference": "foo",
                    "amount": 100
                }
                """;
        final var transaction = new Transaction(jsonData);

        when(repository.save(any(Transaction.class)))
                .thenReturn(transaction);

        final var result = service.createTransaction(jsonData);

        assertEquals(result.data(), jsonData);
    }

    @Test
    @DisplayName("WHEN createTransaction with empty data THEN the result is the created transaction with empty data")
    void createTransactionWithEmptyData() {
        final var jsonData = "{}";
        final var transaction = new Transaction(jsonData);

        when(repository.save(any(Transaction.class)))
                .thenReturn(transaction);

        final var result = service.createTransaction(jsonData);

        assertEquals(result.data(), "{}");
    }

    @Test
    @DisplayName("WHEN updateTransaction called with valid ID THEN the result is the transaction")
    void updateTransactionSuccess() {
        final var id = 1L;
        final var updateData = """
                {
                    "reference": "foo",
                    "amount": 100
                }
                """;
        final var existingTransaction = new Transaction("{ \"amount\": 100 }");

        when(repository.findById(id))
                .thenReturn(Optional.of(existingTransaction));
        when(repository.save(any(Transaction.class)))
                .thenReturn(new Transaction(updateData));

        final var result = service.updateTransaction(id, updateData);

        assertEquals(result.data(), updateData);
    }

    @Test
    @DisplayName("WHEN updateTransaction called with invalid ID THEN the result is transactionNotFoundException")
    void testUpdateTransactionNotFound() {
        final var id = 1L;

        final var exception = assertThrows(
                TransactionNotFoundException.class,
                () -> service.updateTransaction(id, "{}"));

        final var expectedMessage = "Transaction not found with ID: " + id;
        final var resultMessage = exception.getMessage();

        assertEquals(resultMessage, expectedMessage);
    }
}

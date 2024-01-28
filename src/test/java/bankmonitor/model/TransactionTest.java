package bankmonitor.model;

import bankmonitor.model.entity.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionTest {

    @Test
    @DisplayName("WHEN transaction created THEN the result is the transaction")
    void createTransaction() {
        final var jsonData = """
                {
                    "reference": "foo",
                    "amount": 300
                }
                """;
        final var transaction = new Transaction(jsonData);

        assertNotNull(transaction.getTimestamp());
        assertEquals(transaction.getReference(), "foo");
        assertEquals(transaction.getAmount(), 300);
        assertEquals(transaction.getData(), jsonData);
    }

    @Test
    @DisplayName("WHEN transaction created without amount THEN the result is the transaction with default amount")
    void getAmount() {
        final var transaction = new Transaction("{}");

        assertEquals(transaction.getAmount(), -1);
    }

    @Test
    @DisplayName("WHEN transaction created without amount THEN the result is the transaction with default amount")
    void getReference() {
        final var transaction = new Transaction("{}");

        assertEquals(transaction.getReference(), "");
    }

}
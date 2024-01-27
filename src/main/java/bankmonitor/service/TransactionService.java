package bankmonitor.service;

import bankmonitor.exceptions.TransactionNotFoundException;
import bankmonitor.model.api.TransactionDto;
import bankmonitor.model.entity.Transaction;
import bankmonitor.model.mapper.TransactionMappers;
import bankmonitor.repository.TransactionRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMappers mappers;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
        this.mappers = new TransactionMappers();
    }

    public List<TransactionDto> findAll() {
        return mappers.toApi(
                transactionRepository.findAll());
    }

    public TransactionDto createTransaction(String data) {
        return mappers.toApi(
                transactionRepository.save(new Transaction(data)));
    }

    public TransactionDto updateTransaction(Long id, String updateData) {
        return transactionRepository.findById(id)
                .map(transaction -> updateTransaction(transaction, updateData))
                .map(transactionRepository::save)
                .map(mappers::toApi)
                .orElseThrow(
                        () -> new TransactionNotFoundException("Transaction not found with ID: " + id));
    }

    private Transaction updateTransaction(Transaction transaction, String updateData) {
        final var updateJson = new JSONObject(updateData);
        final var dataToUpdate = new JSONObject(transaction.getData());

        if (updateJson.has("amount")) {
            dataToUpdate.put("amount", updateJson.getInt("amount"));
        }

        if (updateJson.has(Transaction.REFERENCE_KEY)) {
            dataToUpdate.put(Transaction.REFERENCE_KEY, updateJson.getString(Transaction.REFERENCE_KEY));
        }

        return Transaction.builder()
                .id(transaction.getId())
                .timestamp(transaction.getTimestamp())
                .data(dataToUpdate.toString())
                .build();
    }
}

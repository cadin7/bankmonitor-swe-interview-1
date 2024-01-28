package bankmonitor.model.mapper;

import bankmonitor.model.api.TransactionDto;
import bankmonitor.model.entity.Transaction;

public class TransactionMappers implements Mappers<TransactionDto, Transaction> {
    @Override
    public ModelMapper<Transaction, TransactionDto> toApiMapper() {
        return new TransactionMapper();
    }
}

class TransactionMapper implements ModelMapper<Transaction, TransactionDto> {

    @Override
    public TransactionDto nullSafeMap(Transaction source) {
        return TransactionDto.builder()
                .id(source.getId())
                .timestamp(source.getTimestamp())
                .data(source.getData())
                .build();
    }
}
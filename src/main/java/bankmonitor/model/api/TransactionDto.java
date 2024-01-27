package bankmonitor.model.api;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransactionDto(long id, LocalDateTime timestamp, String data) {
}

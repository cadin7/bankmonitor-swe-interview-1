package bankmonitor.controller;

import bankmonitor.model.api.TransactionDto;
import bankmonitor.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("transactions")
    public List<TransactionDto> getAllTransactions() {
        return transactionService.findAll();
    }

    @PostMapping("transactions")
    public TransactionDto createTransaction(@RequestBody String jsonData) {
        return transactionService.createTransaction(jsonData);
    }

    @PutMapping("transactions/{id}")
    public TransactionDto updateTransaction(@PathVariable Long id, @RequestBody String update) {
        return transactionService.updateTransaction(id, update);
    }
}
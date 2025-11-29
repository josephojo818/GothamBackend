package Siddharth.demo.controller;


import Siddharth.demo.entity.Transaction;
import Siddharth.demo.entity.TransactionRequest;
import Siddharth.demo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit/{userId}")
    public ResponseEntity<?> deposit(@PathVariable String userId,
                                     @RequestBody TransactionRequest request){
        Transaction t = transactionService.deposit(
                userId,
                request.getAmount(),
                request.getPin());
        return ResponseEntity.ok(t);
    }

    @PostMapping("withdraw/{userId}")
    public ResponseEntity<?> withdraw(@PathVariable String userId,
                                      @RequestBody TransactionRequest request){
        Transaction t = transactionService.withdraw(
                userId,
                request.getAmount(),
                request.getPin());
        return ResponseEntity.ok(t);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTransaction(@PathVariable String userId){
        return ResponseEntity.ok(transactionService.getTransactionsByUser(userId));
    }


}

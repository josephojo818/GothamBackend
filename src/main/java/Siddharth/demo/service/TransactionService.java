package Siddharth.demo.service;


import Siddharth.demo.entity.Transaction;
import Siddharth.demo.entity.User;
import Siddharth.demo.model.TransactionType;
import Siddharth.demo.repository.TransactionRepository;
import Siddharth.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    public Transaction deposit(String userId, Double amount, String pin){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        if(!passwordEncoder.matches(pin, user.getPin())){
            throw new RuntimeException("Invalid pin");
        }

        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);

        Transaction txn = new Transaction();
        txn.setAmount(amount);
        txn.setType(TransactionType.DEPOSIT);
        txn.setUser(user);

        return transactionRepository.save(txn);
    }

    public Transaction withdraw(String userId, Double amount, String pin){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        if(!passwordEncoder.matches(pin, user.getPin())){
            throw new RuntimeException("Invalid pin");
        }

        if (user.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds!");
        }

        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);

        Transaction txn = new Transaction();
        txn.setAmount(amount);
        txn.setType(TransactionType.WITHDRAW);
        txn.setUser(user);

        return transactionRepository.save(txn);
    }



    public List<Transaction> getTransactionsByUser(String userId) {
        return transactionRepository.findByUser_Id(userId);
    }
}
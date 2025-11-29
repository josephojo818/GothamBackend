package Siddharth.demo.service;


import Siddharth.demo.entity.User;
import Siddharth.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        if (user.getRoles() == null) {
            user.setRoles("CUSTOMER");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPin(passwordEncoder.encode(user.getPin()));
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public Double checkBalance(String userId){
        User user = userRepository.findById(userId)
                .orElseThrow();
        return user.getBalance();
    }

    public String setUserName(String id, String userName, String password){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not fount"));
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return user.getUserName();
    }
}

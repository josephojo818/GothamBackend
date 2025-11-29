package Siddharth.demo.controller;



import Siddharth.demo.entity.User;
import Siddharth.demo.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class publicController {

    @Autowired
    private UserService userService;

    @GetMapping("/api")
    public String health(){
        return "Hello my good sire!";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createAccount(@RequestBody User user){
        if(user.getId() == null || user.getId().isEmpty()){
            user.setId(new ObjectId().toString());
        }
        userService.createUser(user);
        return ResponseEntity.status(201).body(user);
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
            return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/balance/{userId}")
    public ResponseEntity<?> checkBalance(@PathVariable String userId){
        return ResponseEntity.ok("Balance: $"+userService.getUserById(userId).getBalance());
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUserName(@RequestBody User user, @PathVariable String userId){
        return ResponseEntity.ok("Your new userName: "+userService
                .setUserName(userId, user.getUserName(), user.getPassword()));
    }
}

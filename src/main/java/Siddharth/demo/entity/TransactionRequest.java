package Siddharth.demo.entity;

import lombok.Data;

@Data
public class TransactionRequest {

    private Double amount;
    private String pin;
}

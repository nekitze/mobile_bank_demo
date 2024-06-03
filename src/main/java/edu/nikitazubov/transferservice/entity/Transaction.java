package edu.nikitazubov.transferservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long senderWalletId;

    private Long recipientWalletId;

    private Long amount;

    private LocalDateTime createdAt;
}

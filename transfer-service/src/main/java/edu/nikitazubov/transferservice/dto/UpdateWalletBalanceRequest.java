package edu.nikitazubov.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateWalletBalanceRequest {
    private Long walletId;
    private Long newBalance;
}

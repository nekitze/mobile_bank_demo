package edu.nikitazubov.transferservice.feign;

import edu.nikitazubov.transferservice.dto.UpdateWalletBalanceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("wallet-service")
public interface WalletClient {
    @GetMapping("/wallets/balance")
    public ResponseEntity<?> getWalletBalance(@RequestParam("walletId") Long walletId);

    @PostMapping("/wallets/updateBalance")
    public ResponseEntity<?> updateBalance(@RequestBody UpdateWalletBalanceRequest request);
}

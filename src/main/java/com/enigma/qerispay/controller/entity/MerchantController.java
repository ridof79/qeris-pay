package com.enigma.qerispay.controller.entity;

import com.enigma.qerispay.auth.CustomUserDetails;
import com.enigma.qerispay.dto.entity.MerchantDTO;
import com.enigma.qerispay.entiy.Merchant;
import com.enigma.qerispay.service.MerchantService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import com.enigma.qerispay.utils.constant.InsertDataConstant;
import com.enigma.qerispay.utils.customResponse.Response;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.MERCHANT_PATH)
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
public class MerchantController {

    MerchantService merchantService;

    @PutMapping
    public ResponseEntity<?> updateMerchant(@RequestBody Merchant merchant, Authentication authentication) {
        if (merchant.getId().equals(((CustomUserDetails) authentication.getPrincipal()).getId())) {
            MerchantDTO updatedMerchant = merchantService.updateMerchant(merchant);
            Response<?> response = new Response<>("Successfully update merchant data", updatedMerchant);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            return new ResponseEntity<>("Unauthorized.", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/list")
    public List<Merchant> getAllMerchant() {
        return merchantService.getAllMerchant();
    }

    @GetMapping("/{id}")
    public Merchant getMerchantById(@PathVariable String id) {
        return merchantService.getMerchantById(id);
    }

    @GetMapping("/{nib}")
    public Merchant getMerchantByNIB(@PathVariable String nib) {
        return merchantService.getMerchantByNIB(nib);
    }

    @DeleteMapping("/{id}")
    public void deleteMerchantById(@PathVariable String id) {
        merchantService.deleteMerchant(id);
    }

    @PostMapping
    public ResponseEntity<Response<Merchant>> saveMerchant(@RequestBody Merchant merchant) {
        Response<Merchant> response = new Response<>();
        response.setMessage(InsertDataConstant.INSERT_MERCHANT_SUCCES);
        response.setData(merchantService.saveMerchant(merchant));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}

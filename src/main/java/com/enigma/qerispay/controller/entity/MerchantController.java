package com.enigma.qerispay.controller.entity;

import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Merchant;
import com.enigma.qerispay.service.MerchantService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import com.enigma.qerispay.utils.constant.InsertDataConstant;
import com.enigma.qerispay.utils.customResponse.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.MERCHANT_PATH)
@AllArgsConstructor
public class MerchantController {
    MerchantService merchantService;

    @GetMapping("/list")
    public List<Merchant> getAllMerchant() {
        return merchantService.getAllMerchant();
    }

    @PutMapping
    public Merchant updateMerchant(@RequestBody Merchant merchant) {
        return merchantService.updateMerchant(merchant);
    }

    @GetMapping("/{id}")
    public Merchant getMerchantById(@PathVariable String id) {
        return merchantService.getMerchantById(id);
    }

    @GetMapping("/search")
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

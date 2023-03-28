package com.enigma.qerispay.controller;

import com.enigma.qerispay.entiy.Customer;
import com.enigma.qerispay.entiy.Merchant;
import com.enigma.qerispay.entiy.Reward;
import com.enigma.qerispay.service.CustomerService;
import com.enigma.qerispay.service.MerchantService;
import com.enigma.qerispay.service.RewardService;
import com.enigma.qerispay.utils.constant.ApiUrlConstant;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrlConstant.SEARCH_PATH)
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
public class SearchController {

    CustomerService customerService;
    MerchantService merchantService;
    RewardService rewardService;

    @GetMapping("/customer")
    public Customer findCustomerByPhone(@RequestParam(name = "phone") String customerPhone) {
        return customerService.getCustomerByPhone(customerPhone);
    }

    @GetMapping("/merchant/{nib}")
    public Merchant getMerchantByNIB(@PathVariable String nib) {
        return merchantService.getMerchantByNIB(nib);
    }

}

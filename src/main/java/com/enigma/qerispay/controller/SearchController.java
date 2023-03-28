package com.enigma.qerispay.controller;

import com.enigma.qerispay.dto.entity.CustomerDTO;
import com.enigma.qerispay.dto.entity.MerchantDTO;
import com.enigma.qerispay.service.entity.CustomerService;
import com.enigma.qerispay.service.entity.MerchantService;
import com.enigma.qerispay.service.entity.RewardService;
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
    public CustomerDTO findCustomerByPhone(@RequestParam(name = "phone") String customerPhone) {
        return customerService.getCustomerByPhone(customerPhone);
    }

    @GetMapping("/merchant/{nib}")
    public MerchantDTO getMerchantByNIB(@PathVariable String nib) {
        return merchantService.getMerchantByNIB(nib);
    }
}

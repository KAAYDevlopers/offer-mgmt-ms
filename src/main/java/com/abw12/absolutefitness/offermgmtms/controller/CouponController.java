package com.abw12.absolutefitness.offermgmtms.controller;

import com.abw12.absolutefitness.offermgmtms.dto.CouponValidationReq;
import com.abw12.absolutefitness.offermgmtms.dto.CouponsDTO;
import com.abw12.absolutefitness.offermgmtms.service.CouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/coupon/v1")
public class CouponController {

    @Autowired
    private CouponService couponService;

    private static final Logger logger = LoggerFactory.getLogger(CouponController.class);

    @PostMapping("/addCoupon")
    public ResponseEntity<?> addCoupon(@RequestBody CouponsDTO request) {
        logger.info("Inside addCoupon Rest API");
        try {
            return new ResponseEntity<>(couponService.addCoupon(request), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception while adding a new coupon :: ERROR Msg={} => {}", e.getMessage(),e.getStackTrace());
            throw e;
        }
    }

    @GetMapping("/validateCoupon")
    public ResponseEntity<?> validateCoupon(@RequestParam("couponCode") String couponCode,
                                            @RequestParam("variantIds") List<String> variantIds){
        logger.info("Inside validateCoupon rest API call : couponCode={} => variantId={}",couponCode,variantIds);
        CouponValidationReq request = new CouponValidationReq(couponCode,new HashSet<>(variantIds));
        try{
            return new ResponseEntity<>(couponService.validateCoupon(request),HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception while validating coupon :: ERROR Msg={} => {}", e.getMessage(),e.getStackTrace());
            throw e;
        }
    }

    @GetMapping("/listCoupons/{userId}")
    public ResponseEntity<?> listCoupons(@PathVariable String userId){
        logger.info("Inside listCoupons rest API call.");
        try{
            return new ResponseEntity<>(couponService.listAllCoupons(userId),HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception in listCoupons :: ERROR Msg={} => {}", e.getMessage(),e.getStackTrace());
            throw e;
        }
    }
}
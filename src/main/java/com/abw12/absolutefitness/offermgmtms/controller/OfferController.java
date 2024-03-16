package com.abw12.absolutefitness.offermgmtms.controller;

import com.abw12.absolutefitness.offermgmtms.dto.CalcOfferRequest;
import com.abw12.absolutefitness.offermgmtms.dto.OffersDTO;
import com.abw12.absolutefitness.offermgmtms.helper.Utils;
import com.abw12.absolutefitness.offermgmtms.service.OfferService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offer/v1")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private Utils utils;

    private static final Logger logger = LoggerFactory.getLogger(OfferController.class);

    @PostMapping("/addOffer")
    public ResponseEntity<?> addOffer(@RequestBody OffersDTO request){
    logger.info("Inside addOffer Rest API");
     try{
        return new ResponseEntity<>(offerService.createOffers(request), HttpStatus.OK);
     }catch (Exception e){
         logger.error("Exception while adding a new offer :: ERROR Msg={} => {}",e.getMessage(),e.getStackTrace());
         throw e;
     }
    }

    @GetMapping("/getOfferDetails/{offerId}")
    public ResponseEntity<?> getOfferDetails(@PathVariable String offerId){
        logger.info("Inside getOfferDetails Rest API");
        try{
            return new ResponseEntity<>(offerService.getOfferDetails(offerId), HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception while Fetching an offer with offerId={} :: ERROR Msg={} => {}",offerId,e.getMessage(),e.getStackTrace());
            throw e;
        }
    }

    @PostMapping("/calculateDiscount")
    public ResponseEntity<?> calculateDiscount(@RequestBody CalcOfferRequest request){
        logger.info("Inside calculateDiscount Rest API");
        if(request == null || StringUtils.isEmpty(request.getOfferId())){
            logger.error("offerId cannot be null/empty in calculate Discount Offer Request :: Request data => {}",request);
            return new ResponseEntity<>("Invalid request offerId cannot be null/emtpy",HttpStatus.BAD_REQUEST);
        }
        try{
            return new ResponseEntity<>(offerService.calcDiscount(request), HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception while calculating discount for requested offer with offerId={} :: ERROR Msg={} => {}",request.getOfferId(),e.getMessage() ,e.getStackTrace());
            throw e;
        }
    }
}

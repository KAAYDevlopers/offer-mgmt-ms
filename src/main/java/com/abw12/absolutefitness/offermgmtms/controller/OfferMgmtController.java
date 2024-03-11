package com.abw12.absolutefitness.offermgmtms.controller;

import com.abw12.absolutefitness.offermgmtms.dto.OffersDTO;
import com.abw12.absolutefitness.offermgmtms.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offerMgmt")
public class OfferMgmtController {

    @Autowired
    private OfferService offerService;

    private static final Logger logger = LoggerFactory.getLogger(OfferMgmtController.class);

    @PostMapping("/addOffer")
    public ResponseEntity<?> addOffer(@RequestBody OffersDTO request){
    logger.info("Inside addOffer Rest API");
     try{
        return new ResponseEntity<>(offerService.createOffers(request), HttpStatus.OK);
     }catch (Exception e){
         logger.error("Exception while adding a new offer/coupon :: Error Message= {}",e.getMessage());
         throw e;
     }
    }


    @GetMapping("/getOfferDetails/{offerId}")
    public ResponseEntity<?> getOfferDetails(@PathVariable String offerId){
        logger.info("Inside getOfferDetails Rest API");
        try{
            return new ResponseEntity<>(offerService.getOfferDetails(offerId), HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception while Fetching an offer with offerId={} :: Error Message= {}",offerId,e.getMessage());
            throw e;
        }
    }
}

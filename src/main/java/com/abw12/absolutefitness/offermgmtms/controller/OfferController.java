package com.abw12.absolutefitness.offermgmtms.controller;

import com.abw12.absolutefitness.offermgmtms.dto.CalcOfferRequest;
import com.abw12.absolutefitness.offermgmtms.dto.OfferVariantDTO;
import com.abw12.absolutefitness.offermgmtms.dto.OffersDTO;
import com.abw12.absolutefitness.offermgmtms.helper.Utils;
import com.abw12.absolutefitness.offermgmtms.mapper.RequestDTOMapper;
import com.abw12.absolutefitness.offermgmtms.service.OfferService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/offer/v1")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private Utils utils;

    private static final Logger logger = LoggerFactory.getLogger(OfferController.class);

    @PostMapping("/addOffer")
    public ResponseEntity<?> addOffer(@RequestBody @Valid OffersDTO request){
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
    public ResponseEntity<?> calculateDiscount(@RequestBody @Valid @NotNull CalcOfferRequest request){
        logger.info("Inside calculateDiscount Rest API");
        try{
           return new ResponseEntity<>(offerService.calcDiscount(request), HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception while calculating discount for requested offer with offerId={} :: ERROR Msg={} => {}",request.getOfferId(),e.getMessage() ,e.getStackTrace());
            throw e;
        }
    }


    @PostMapping("/mapVariantIdToOffer")
    public ResponseEntity<?> mapVariantIdToOffer(@RequestBody Map<String,Object> request){
        logger.info("Inside calculateDiscount Rest API");
        OfferVariantDTO offerVariantDTO = RequestDTOMapper.mapRequestParamsToOfferVariantDTO(request);
        try{
            return new ResponseEntity<>(offerService.mapVariantIdToOffer(offerVariantDTO), HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception while mapping variantId={} to offer with offerId={} :: ERROR Msg={} => {}",offerVariantDTO.getVariantId(),offerVariantDTO.getOfferId(),e.getMessage() ,e.getStackTrace());
            throw e;
        }
    }
}

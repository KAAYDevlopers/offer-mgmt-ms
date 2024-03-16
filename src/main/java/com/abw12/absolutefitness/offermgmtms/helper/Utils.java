package com.abw12.absolutefitness.offermgmtms.helper;

import com.abw12.absolutefitness.offermgmtms.constants.Constants;
import com.abw12.absolutefitness.offermgmtms.dto.*;
import com.abw12.absolutefitness.offermgmtms.entity.OfferConditionDAO;
import com.abw12.absolutefitness.offermgmtms.entity.OffersDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public Set<CouponVariantDTO> filterApplicableVariants(CouponsDTO couponsDTO, CouponValidationReq req){
        Set<CouponVariantDTO> applicableVariantIds=null;
        if(!req.getVariantIds().isEmpty()){
            applicableVariantIds = req.getVariantIds().stream().flatMap(variantId ->
                    couponsDTO.getApplicableVariantIds().stream().filter(couponVariantDTO -> couponVariantDTO.getVariantId().equalsIgnoreCase(variantId))
            ).collect(Collectors.toSet());
        }
        return applicableVariantIds;
    }

    public boolean checkIfCouponIsApplicable(CouponsDTO couponsDTO){
        return couponsDTO.getIsActive() && couponsDTO.getUsageLimit() > 0;
    }

    public boolean validateMarkUserEntryForCouponReq(Map<String,Object> req){
        return req == null || !req.containsKey("userId") || !req.containsKey("couponId");
    }

    public boolean evaluteOfferCondition(CalcOfferRequest request, Set<OfferConditionDAO> offerConditions) {
        Optional<OfferConditionDAO> matchingCondition = offerConditions.stream().takeWhile(offerCondition ->
                request.getCategoryName().equalsIgnoreCase(offerCondition.getCategoryName())
                        && request.getBrandName().equalsIgnoreCase(offerCondition.getBrandName())
                        && request.getProductName().equalsIgnoreCase(offerCondition.getProductName())
                        && request.getVariantName().equalsIgnoreCase(offerCondition.getVariantName())
        ).findFirst();

        return matchingCondition.isPresent();
    }

    public CalcOfferResponse calculateOnSalePrice(OffersDAO offerData, BigDecimal buyPrice) {
        CalcOfferResponse response = new CalcOfferResponse();

        String discountType = offerData.getDiscountType();
        BigDecimal hundred = new BigDecimal(Constants.HUNDRED);
        //calculate price by percentage
        if(discountType.equalsIgnoreCase(Constants.DISCOUNT_TYPE_PERCENT)){

            BigDecimal discountValue = new BigDecimal(offerData.getDiscountValue());
            // Convert discount rate from percentage to fraction
            BigDecimal  discountRate = discountValue.divide(hundred);
            // Calculate the discount amount
            BigDecimal discountAmount = buyPrice.multiply(discountRate);
            // Calculate the on-sale price
            BigDecimal onSalePrice = buyPrice.subtract(discountAmount);
            // rounding the on-sale price by 2 decimal
            response.setOnSalePrice(onSalePrice.setScale(2, RoundingMode.HALF_UP));
        }
        //calculate price by fixed amount
        if(discountType.equalsIgnoreCase(Constants.DISCOUNT_TYPE_FIXED)){
            // fixed price to substract in Bigdecimal
            BigDecimal fixedAmount = new BigDecimal(offerData.getDiscountValue());
            // Calculate the on-sale price for fixed amount
            BigDecimal onSalePrice = buyPrice.subtract(fixedAmount);
            response.setOnSalePrice(onSalePrice);
        }
        response.setMsg(String.format("Successfully calculated the discount and updated onSalePrice by applying the offer with offerId=%s",offerData.getOfferId()));
        response.setStatusCode(HttpStatus.OK.getReasonPhrase());
        logger.info("Successfully calculated the discount and updated onSalePrice={} by applying the offer with offerId={}",response.getOnSalePrice(),offerData.getOfferId());
        return response;
    }

    public String offsetDateTimeFormatter(OffsetDateTime dateField){
        if(dateField==null) return "";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return dateField.format(fmt);
    }

    public static DateTimeFormatter dateFormat(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }
}

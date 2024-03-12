package com.abw12.absolutefitness.offermgmtms.service;

import com.abw12.absolutefitness.offermgmtms.advice.InvalidDataRequestException;
import com.abw12.absolutefitness.offermgmtms.dto.*;
import com.abw12.absolutefitness.offermgmtms.entity.CouponVariantDAO;
import com.abw12.absolutefitness.offermgmtms.entity.CouponsDAO;
import com.abw12.absolutefitness.offermgmtms.helper.Utils;
import com.abw12.absolutefitness.offermgmtms.mapper.CouponVariantMapper;
import com.abw12.absolutefitness.offermgmtms.mapper.CouponsMapper;
import com.abw12.absolutefitness.offermgmtms.repository.CouponVariantRepository;
import com.abw12.absolutefitness.offermgmtms.repository.CouponsRepository;
import com.abw12.absolutefitness.offermgmtms.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CouponService {

    @Autowired
    private CouponsRepository couponsRepository;
    @Autowired
    private CouponVariantRepository couponVariantRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CouponsMapper couponsMapper;

    @Autowired
    private CouponVariantMapper couponVariantMapper;
    @Autowired
    private Utils utils;


    private static final Logger logger = LoggerFactory.getLogger(CouponService.class);
    @Transactional
    public String addCoupon(CouponsDTO request){
        logger.info("Request to add new coupon in db :: {}",request);
        CouponsDAO couponToSave = couponsMapper.dtoToEntity(request);
        CouponsDAO storedData = couponsRepository.save(couponToSave);
        UUID couponId = storedData.getCouponId();

        //store variant set in coupon variant table (which tells on which product this coupon is applicable)
        request.getApplicableVariantIds().forEach(couponVariantDTO -> {
            CouponVariantDAO couponVariantToSave = couponVariantMapper.dtoToEntity(couponVariantDTO);
            couponVariantToSave.setCouponId(couponId);
            try{
                couponVariantRepository.save(couponVariantToSave);
            }catch (Exception e){
                logger.error("error while adding coupon variant data for couponId={} => ERROR :: {}",couponId.toString(),e.getMessage());
            }
            logger.info("variant data = {} for couponId={} is stored",couponVariantToSave,couponId.toString());
        });
        logger.info("Successfully stored new coupon data in DB with couponId={}",couponId.toString());
        return String.format("Successfully stored new coupon data in DB with couponId=%s",couponId.toString());
    }

    @Transactional(readOnly = true)
    public Map<String,Object> validateCoupon(CouponValidationReq req){
        logger.info("Validating coupon code if applicable for requested variantId list =>  couponCode={} => list={}",req.getCouponCode(),req.getVariantIds());
        //check if the coupon code exist in db for requested couponId
        CouponsDAO couponEntity = couponsRepository.fetchCouponByCode(req.getCouponCode()).orElseThrow(() -> new InvalidDataRequestException(
                String.format("Exception while fetching coupon code cannot find any coupon with couponCode=%s", req.getCouponCode())));
        CouponsDTO couponDTO = couponsMapper.entityToDto(couponEntity);
        //fetch applicable variantIds data for the fetched coupon
        Set<CouponVariantDAO> couponVariantIdEntity = couponVariantRepository.fetchApplicableVariantIds(couponEntity.getCouponId()).orElseThrow(() -> new InvalidDataRequestException(
                String.format("Exception while fetching applicable variant ids for coupon cannot find any variant id having couponId=%s", couponEntity.getCouponId())));
        Set<CouponVariantDTO>  variantIds = couponVariantIdEntity.stream().map(couponVariantDAO ->  couponVariantMapper.entityToDto(couponVariantDAO)).collect(Collectors.toSet());
        couponDTO.setApplicableVariantIds(variantIds);

        //check if coupon is active and available( usage limit is > 0 )
        if(!utils.checkIfCouponIsApplicable(couponDTO)){
            logger.info("coupon is not applicable...");
            //mark coupon as inActive if usageLimit is <=0
            if(couponDTO.getUsageLimit() <= 0){
                logger.info("coupon usage limit has exceeded setting the coupon to in-active state");
                CouponsDAO updatedCoupon = couponsMapper.dtoToEntity(couponDTO);
                updatedCoupon.setIsActive(false);
                CouponsDAO latestCouponDataStored = couponsRepository.save(updatedCoupon);
                logger.info("successfully mark the coupon as in-active :: latest coupon data => {}",latestCouponDataStored);
            }
            return Map.of("result", new ErrorResponse("coupon is not active or usage limit has been exceeded for this coupon", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }

        //fetch only applicable variant id on which this coupon can be applied
        Set<CouponVariantDTO> applicableVariantIds = utils.filterApplicableVariants(couponDTO, req);
        if(applicableVariantIds == null || applicableVariantIds.isEmpty()){
            logger.info("No variant requested exist in the coupons applicable variantId list");
            return Map.of("result",new ErrorResponse("coupon code does not apply to any variant requested", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
        logger.info("Coupon is applicable for variantIds={}",applicableVariantIds);
        return Map.of("result",new CouponValidationRes(couponDTO,applicableVariantIds,"success",HttpStatus.OK.getReasonPhrase()));
    }





}

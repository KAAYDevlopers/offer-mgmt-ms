package com.abw12.absolutefitness.offermgmtms.service;

import com.abw12.absolutefitness.offermgmtms.advice.InvalidDataRequestException;
import com.abw12.absolutefitness.offermgmtms.dto.*;
import com.abw12.absolutefitness.offermgmtms.entity.CouponVariantDAO;
import com.abw12.absolutefitness.offermgmtms.entity.CouponsDAO;
import com.abw12.absolutefitness.offermgmtms.helper.Utils;
import com.abw12.absolutefitness.offermgmtms.mapper.CouponVariantMapper;
import com.abw12.absolutefitness.offermgmtms.mapper.CouponsMapper;
import com.abw12.absolutefitness.offermgmtms.mapper.CustomerEntriesMapper;
import com.abw12.absolutefitness.offermgmtms.repository.CouponVariantRepository;
import com.abw12.absolutefitness.offermgmtms.repository.CouponsRepository;
import com.abw12.absolutefitness.offermgmtms.repository.CustomerRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private CustomerEntriesMapper customerEntriesMapper;
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

    @Transactional(readOnly = true)
    public List<CouponsDTO> listAllCoupons(String userId){
        logger.info("List all the coupons available requested for userId={}",userId);
        if(StringUtils.isEmpty(userId)){
            //guest user - fetch and list all the available coupons
            List<CouponsDTO> couponsFromDB = fetchAllCouponsFromDB();
            List<CouponsDTO> response = fetchAndMapApplicableVariantIds(couponsFromDB);
            logger.info("Fetched all coupons available from DB :: {}",response);
            return response;
        }else{
            //check the customerentries table to filter out the coupons user have already used before from the list
            List<CouponsDTO> couponsFromDB = fetchAllCouponsFromDB();
            //fetch couponIds already used by the customer
            Set<String> utilizedCouponIds = customerRepository.getUserEntries(userId).stream()
                    .map(entry -> customerEntriesMapper.entityToDto(entry))
                    .map(CustomerDTO::getCouponId)
                    .collect(Collectors.toSet());
            if(!utilizedCouponIds.isEmpty()){
                //filtering the couponId already present in the customerentries table which mark the already used coupons by the customer
                List<CouponsDTO> filteredCoupons = couponsFromDB.stream()
                        .filter(couponsDTO -> utilizedCouponIds.contains(couponsDTO.getCouponId()))
                        .toList();
                List<CouponsDTO> response = fetchAndMapApplicableVariantIds(filteredCoupons);
                logger.info("Fetched all coupons available from DB after filtering the used coupons for userId={} :: {}",userId,response);
                return response;
            }
            List<CouponsDTO> response = fetchAndMapApplicableVariantIds(couponsFromDB);
            logger.info("Fetched all coupons available from DB for userId={} :: {}",userId,response);
            return response;
        }
    }


    private List<CouponsDTO> fetchAllCouponsFromDB(){
        return couponsRepository.findAll().stream()
                .map(couponsDAO -> couponsMapper.entityToDto(couponsDAO))
                .toList();
    }

    private List<CouponsDTO> fetchAndMapApplicableVariantIds(List<CouponsDTO> couponsFromDB){
        return couponsFromDB.stream().peek(couponsDTO -> {
            String couponId = couponsDTO.getCouponId();
            Set<CouponVariantDAO> applicableVariantIdsFromDB = couponVariantRepository.fetchApplicableVariantIds(UUID.fromString(couponId)).orElseThrow(() -> new InvalidDataRequestException(
                    String.format("Exception while fetching applicable variant ids for coupon cannot find any variant id having couponId=%s", couponId)));
            Set<CouponVariantDTO> variantIdsDTO = applicableVariantIdsFromDB.stream()
                    .map(applicableVariantId -> couponVariantMapper.entityToDto(applicableVariantId))
                    .collect(Collectors.toSet());
            couponsDTO.setApplicableVariantIds(variantIdsDTO);
        }).toList();
    }







}

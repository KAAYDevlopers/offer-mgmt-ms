package com.abw12.absolutefitness.offermgmtms.service;

import com.abw12.absolutefitness.offermgmtms.dto.CouponsDTO;
import com.abw12.absolutefitness.offermgmtms.entity.CouponVariantDAO;
import com.abw12.absolutefitness.offermgmtms.entity.CouponsDAO;
import com.abw12.absolutefitness.offermgmtms.mapper.CouponVariantMapper;
import com.abw12.absolutefitness.offermgmtms.mapper.CouponsMapper;
import com.abw12.absolutefitness.offermgmtms.repository.CouponVariantRepository;
import com.abw12.absolutefitness.offermgmtms.repository.CouponsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    private CouponsRepository couponsRepository;

    @Autowired
    private CouponsMapper couponsMapper;

    @Autowired
    private CouponVariantMapper couponVariantMapper;

    @Autowired
    private CouponVariantRepository couponVariantRepository;

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
}

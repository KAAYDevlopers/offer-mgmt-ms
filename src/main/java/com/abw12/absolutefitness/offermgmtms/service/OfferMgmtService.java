package com.abw12.absolutefitness.offermgmtms.service;

import com.abw12.absolutefitness.offermgmtms.advice.InvalidDataRequestException;
import com.abw12.absolutefitness.offermgmtms.dto.OffersDTO;
import com.abw12.absolutefitness.offermgmtms.entity.OfferConditionDAO;
import com.abw12.absolutefitness.offermgmtms.entity.OfferVariantDAO;
import com.abw12.absolutefitness.offermgmtms.entity.OffersDAO;
import com.abw12.absolutefitness.offermgmtms.mapper.OfferConditionMapper;
import com.abw12.absolutefitness.offermgmtms.mapper.OfferVariantMapper;
import com.abw12.absolutefitness.offermgmtms.mapper.OffersMapper;
import com.abw12.absolutefitness.offermgmtms.repository.OfferConditionRepository;
import com.abw12.absolutefitness.offermgmtms.repository.OfferVariantRepository;
import com.abw12.absolutefitness.offermgmtms.repository.OffersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OfferMgmtService {
    @Autowired
    private OffersRepository offersRepository;
    //    @Autowired
//    private CouponsRepository couponsRepository;
    @Autowired
    private OfferConditionRepository offerConditionRepository;

    @Autowired
    private OfferVariantRepository offerVariantRepository;
    @Autowired
    private OffersMapper offersMapper;
    @Autowired
    private OfferConditionMapper offerConditionMapper;

    @Autowired
    private OfferVariantMapper offerVariantMapper;
//    @Autowired
//    private CouponsMapper couponsMapper;

    private static final Logger logger = LoggerFactory.getLogger(OfferMgmtService.class);

    @Transactional
    public String createOffers(OffersDTO request){
        logger.info("Request to add new offer in db :: {}",request);
        OffersDAO offerToSave = offersMapper.dtoToEntity(request);
        OffersDAO storedOffer = offersRepository.save(offerToSave);
        UUID offerId = storedOffer.getOfferId();

        //store offer condition set in offer condition table
        request.getConditions().forEach(conditionDTO -> {
            OfferConditionDAO offerConditionToSave = offerConditionMapper.dtoToEntity(conditionDTO);
            offerConditionToSave.setOfferId(offerId);
            try{
                OfferConditionDAO storedConditionData = offerConditionRepository.save(offerConditionToSave);
            }catch (Exception e){
                logger.error("error while adding condition data for offerId={} => ERROR :: {}",offerId.toString(),e.getMessage());
            }

            logger.debug("condition data = {} for offerId={} is stored.",offerConditionToSave,offerId.toString());
        });

        //store offer variant set in offer variant table (optional if provided while adding the offer)
        request.getApplicableVariantIds().forEach(offerVariantDTO -> {
            OfferVariantDAO offerVariantToSave = offerVariantMapper.dtoToEntity(offerVariantDTO);
            offerVariantToSave.setOfferId(offerId);
            try{
                OfferVariantDAO storedOfferVariant = offerVariantRepository.save(offerVariantToSave);
            }catch (Exception e){
                logger.error("error while adding variant data for offerId={} => ERROR :: {}",offerId.toString(),e.getMessage());
            }
            logger.debug("variant data = {} for offerId={} is stored.",offerVariantToSave,offerId.toString());
        });
        logger.info("Successfully stored new offer data in DB with offerId={}",offerId.toString());
        return String.format("Successfully stored new offer data in DB with offerId=%s",offerId.toString());
    }

    @Transactional(readOnly = true)
    public OffersDTO getOfferDetails(String id){
        logger.info("Fetching offer details with offerId={}",id);
        UUID offerId = UUID.fromString(id);
        OffersDAO offerData = offersRepository.findById(offerId).orElseThrow(() -> new InvalidDataRequestException(
                String.format("Exception while fetching Offer cannot find offer data with offerId=%s", offerId)));
        Set<OfferConditionDAO> offerConditions = offerConditionRepository.getConditions(offerId).orElseThrow(() -> new InvalidDataRequestException(
                String.format("Exception while fetching OfferCondition cannot find any condition data with offerId=%s", offerId)));
        Set<OfferVariantDAO> offerVariantIds = offerVariantRepository.getVariantIds(offerId).orElseThrow(() -> new InvalidDataRequestException(
                String.format("Exception while fetching variant ids for offer with offerId=%s", offerId)));

        OffersDTO response = offersMapper.entityToDto(offerData);
        //set offer conditions in response
        response.setConditions(
                offerConditions.stream()
                        .map(condition -> offerConditionMapper.entityToDto(condition))
                        .collect(Collectors.toSet())
        );
        //set offer variant ids in response
        response.setApplicableVariantIds(
                offerVariantIds.stream()
                        .map(offerVariantId -> offerVariantMapper.entityToDto(offerVariantId))
                        .collect(Collectors.toSet())
        );
        logger.info("Retrieved offer details for offerIds={} => {}",id,response);
        return response;
    }

}

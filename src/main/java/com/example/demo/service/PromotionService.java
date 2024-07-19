package com.example.demo.service;

import com.example.demo.eNum.PromotionStatus;
import com.example.demo.entity.Account;
import com.example.demo.entity.Promotion;
import com.example.demo.exception.GlobalException;
import com.example.demo.model.Request.PromotionRequest;
import com.example.demo.respository.PromotionRepository;
import com.example.demo.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {
    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    AccountUtils accountUtils;



    public List<Promotion> getPromotionsByLocation(long id) {
        return promotionRepository.findByLocationId(id).stream().filter(p -> p.getStatus().equals(PromotionStatus.ACTIVE)).toList();
    }

    public Promotion createPromotion(PromotionRequest promotionRequest) {
        Account account = accountUtils.getCurrentUser();
        Promotion promotion = new Promotion();
        promotion.setCode(promotionRequest.getCode());
        promotion.setDiscount(promotionRequest.getDiscount());
        promotion.setStartDate(promotionRequest.getStartDate());
        promotion.setEndDate(promotionRequest.getEndDate());
        promotion.setStatus(PromotionStatus.ACTIVE);
        promotion.setLocation(account.getLocation());

        account.getLocation().getPromotions().add(promotion);


        return promotionRepository.save(promotion);
    }
    public Promotion updatePromotion(PromotionRequest promotionRequest, Long id) {
            Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> new GlobalException("Promotion not found"));
            promotion.setCode(promotionRequest.getCode());
            promotion.setDiscount(promotionRequest.getDiscount());
            promotion.setStartDate(promotionRequest.getStartDate());
            promotion.setEndDate(promotionRequest.getEndDate());
            promotion.setStatus(promotionRequest.getStatus());
            return promotionRepository.save(promotion);
    }
    public Promotion deletePromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> new GlobalException("Promotion not found"));
        if(promotion.getStatus().equals(PromotionStatus.INACTIVE)) throw new GlobalException("Promotion is inactive");

        promotion.setStatus(PromotionStatus.INACTIVE);
        return promotionRepository.save(promotion);
    }
}

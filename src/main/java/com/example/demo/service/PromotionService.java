package com.example.demo.service;

import com.example.demo.entity.Promotion;
import com.example.demo.model.Request.PromotionRequest;
import com.example.demo.respository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {
    @Autowired
    PromotionRepository promotionRepository;

    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    public Promotion CreatePromotion(PromotionRequest promotionRequest) {
        Promotion promotion = new Promotion();
        promotion.setCode(promotionRequest.getCode());
        promotion.setDiscount(promotionRequest.getDiscount());
        promotion.setStartDate(promotionRequest.getStartDate());
        promotion.setEndDate(promotionRequest.getEndDate());

        return promotionRepository.save(promotion);
    }
    public Promotion UpdatePromotion(PromotionRequest promotionRequest,Long id) {
        Optional<Promotion> promotionupdate = promotionRepository.findById(id);
        if(promotionupdate.isPresent()) {
            Promotion promotion = promotionupdate.get();
            promotion.setCode(promotionRequest.getCode());
            promotion.setDiscount(promotionRequest.getDiscount());
            promotion.setStartDate(promotionRequest.getStartDate());
            promotion.setEndDate(promotionRequest.getEndDate());
            return promotionRepository.save(promotion);
        }
        else return null;
    }
    public void DeletePromotion(Long id) {
        Optional<Promotion> promotion = promotionRepository.findById(id);
        if(promotion.isPresent()) {
            promotionRepository.delete(promotion.get());
        }else {
            System.out.println("Promotion not found");
        }
    }
}

package com.example.demo.controller;

import com.example.demo.entity.Promotion;
import com.example.demo.model.Request.PromotionRequest;
import com.example.demo.respository.PromotionRepository;
import com.example.demo.service.PromotionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name="api")
@CrossOrigin(origins = "*")
public class PromotionAPI {
    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    private PromotionService promotionService;

    @GetMapping("/get_All_Promotion")
    public ResponseEntity getAllPromotion(){
//        promotionRepository.findAll()
        return ResponseEntity.ok("ok");
    }
    @PostMapping("/create_promtion")
    public ResponseEntity<?> createPromotion(@RequestBody PromotionRequest promotionRequest){
        Promotion promotion = promotionService.CreatePromotion(promotionRequest);
        return ResponseEntity.ok().body(promotion);
    }
    @PutMapping("/update_promotion/{id}")
    public ResponseEntity<?> updatePromotion(@RequestBody PromotionRequest promotionRequest,@PathVariable Long id){
        Promotion promotion = promotionService.UpdatePromotion(promotionRequest,id);
        return ResponseEntity.ok().body(promotion);
    }
    @DeleteMapping("/delete_promotion/{id}")
    public ResponseEntity<?> deletePromotion(Long id){
        promotionService.DeletePromotion(id);
        return ResponseEntity.ok("DeleteSuccessful");
    }
}
//
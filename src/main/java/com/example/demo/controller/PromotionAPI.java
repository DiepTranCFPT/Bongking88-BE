package com.example.demo.controller;

import com.example.demo.entity.Promotion;
import com.example.demo.model.Request.PromotionRequest;
import com.example.demo.respository.PromotionRepository;
import com.example.demo.service.PromotionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name="api")
@CrossOrigin(origins = "*")
@RequestMapping("api/promotion")
public class PromotionAPI {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PromotionRepository promotionRepository;
    @GetMapping("{id}")
    public ResponseEntity getPromotionsByLocation(@PathVariable long id){
        List<Promotion> promotionList = promotionService.getPromotionsByLocation(id);
        return ResponseEntity.ok(promotionList);
    }
//    @GetMapping
//    public ResponseEntity getAllPromotion(){
//        List<Promotion> promotionList = promotionService.getAllPromotions();
//        return ResponseEntity.ok(promotionList);
//    }


//    @GetMapping
//    public ResponseEntity getAll(){
//        return ResponseEntity.ok(pro)
//    }

    @PostMapping
    public ResponseEntity<?> createPromotion(@RequestBody PromotionRequest promotionRequest){
        Promotion promotion = promotionService.CreatePromotion(promotionRequest);
        return ResponseEntity.ok().body(promotion);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updatePromotion(@RequestBody PromotionRequest promotionRequest,@PathVariable Long id){
        Promotion promotion = promotionService.UpdatePromotion(promotionRequest,id);
        return ResponseEntity.ok().body(promotion);
    }
    @DeleteMapping("{id}")
    public ResponseEntity deletePromotion(@PathVariable Long id){
        return ResponseEntity.ok(promotionService.DeletePromotion(id));
    }
}
//
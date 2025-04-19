package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.Repository.IngredientDAO;
import hei.phylosius.restomanager.Repository.PriceDAO;
import hei.phylosius.restomanager.Service.IngredientService;
import hei.phylosius.restomanager.dto.IngredientRest;
import hei.phylosius.restomanager.dto.IngredientUpdateRest;
import hei.phylosius.restomanager.dto.StockMovementRest;
import hei.phylosius.restomanager.mappers.IngredientMapper;
import hei.phylosius.restomanager.mappers.StockMovementMapper;
import hei.phylosius.restomanager.model.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/ingredients")
public class IngredientRestController {

    private final IngredientMapper ingredientMapper;
    private final IngredientDAO ingredientDAO;
    private final PriceDAO priceDAO;
    private final StockMovementMapper stockMovementMapper;
    private final IngredientService ingredientService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneIngredient(@PathVariable Integer id) {
        if (ingredientService.isExist(id)) {
            return ResponseEntity.ok(ingredientService.getOneIngredient(id));
        } else {
            return ResponseEntity.status(500).body(String.format("Ingredient with id %s not found", id));
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getIngredients(@RequestParam(required = false) Integer page,
                                            @RequestParam(required = false) Integer pageSize){

        if (page != null) {
            if (pageSize == null){
                return ResponseEntity.status(400).body("?page should be given with ?pageSize");
            } else if (page < 1 || pageSize < 1) {
                return ResponseEntity.status(400).body("?page and ?pageSize should be greater than 0");
            }
        }

        return ResponseEntity.ok(ingredientService.getIngredients(page, pageSize));
    }

    @PostMapping
    public ResponseEntity<?> createIngredients(@RequestBody List<IngredientRest> ingredientRests) {
        List<Ingredient> ingredients = ingredientRests.stream().map(ingredientMapper::toEntity).toList();

        try {
            ingredientDAO.addAll(ingredients);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

        return ResponseEntity.ok(ingredients);
    }

    @PutMapping
    public ResponseEntity<?> updateIngredients(@RequestBody List<IngredientUpdateRest> ingredientDTOs) {
        List<Ingredient> ingredients = ingredientDTOs.stream().map(ingredientMapper::toEntity).toList();

        ingredientDAO.saveAll(ingredients);

        return ResponseEntity.ok(ingredients);
    }

    @PutMapping("/{ingredientId}/prices")
    public ResponseEntity<?> setPrices(@PathVariable String ingredientId, @RequestBody List<Price> prices) {
        priceDAO.saveAllByIngredientId(ingredientId, prices);

        return ResponseEntity.ok(prices);
    }

    @PutMapping("/{ingredientId}/stockMovement")
    public ResponseEntity<?> setStockMovement(@PathVariable String ingredientId, @RequestBody List<StockMovementRest> dtos) {

        Ingredient affectedIngredient = ingredientDAO.getById(ingredientId);
        dtos.stream().map(dto -> stockMovementMapper.toEntity(affectedIngredient, dto)).toList();

        return ResponseEntity.ok(dtos);
    }
}

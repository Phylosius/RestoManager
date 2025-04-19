package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.Repository.IngredientDAO;
import hei.phylosius.restomanager.Repository.PriceDAO;
import hei.phylosius.restomanager.Service.IngredientService;
import hei.phylosius.restomanager.dto.IngredientRest;
import hei.phylosius.restomanager.dto.IngredientUpdateRest;
import hei.phylosius.restomanager.dto.PriceRest;
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

    @PutMapping("/{id}/prices")
    public ResponseEntity<?> setPrices(@PathVariable Integer id, @RequestBody List<PriceRest> prices) {
        try {
            ingredientService.savePrices(id, prices);

            return ResponseEntity.ok(ingredientService.getOneIngredient(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/stockMovements")
    public ResponseEntity<?> setStockMovement(@PathVariable Integer id, @RequestBody List<StockMovementRest> movements) {

        try {
            ingredientService.addStockMovements(id, movements);

            return ResponseEntity.ok(ingredientService.getOneIngredient(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }
}

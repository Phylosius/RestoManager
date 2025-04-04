package hei.phylosius.restomanager.controller;

import hei.phylosius.restomanager.dao.IngredientDAO;
import hei.phylosius.restomanager.dao.PriceDAO;
import hei.phylosius.restomanager.dto.IngredientDTO;
import hei.phylosius.restomanager.mappers.IngredientMapper;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneIngredient(@PathVariable String id) {
        Ingredient ingredient = ingredientDAO.getById(id);
        if (ingredient == null) {
            return ResponseEntity.status(404).body(String.format("Ingredient=%s is not found", id));
        }

        return ResponseEntity.ok(ingredientMapper.toDTO(ingredient));
    }

    @GetMapping
    public ResponseEntity<?> getIngredients(@RequestParam(required = false) Integer page,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) Double priceMinFilter,
                                            @RequestParam(required = false) Double priceMaxFilter) {
        List<IngredientDTO> ingredientDTOSs;
        List<Ingredient> ingredients = new ArrayList<>();
        List<Criteria> criteria = new ArrayList<>();

        if (priceMinFilter != null && priceMaxFilter != null) {
            if (priceMinFilter > priceMaxFilter) {
                return ResponseEntity.status(400).body("?priceMinFilter should be less or equal to ?priceMaxFilter");
            }
        }

        if (priceMinFilter != null) {
            if (priceMinFilter > -1d) {
                Criteria minPriceCriteria = new Criteria(LogicalOperator.AND, "p.unit_price", CriteriaOperator.GREATER_OR_EQUAL, priceMinFilter);
                criteria.add(minPriceCriteria);
            } else {
                return ResponseEntity.status(400).body("?priceMinFilter should be greater or equal to 0");
            }
        }

        if (priceMaxFilter != null) {
            if (priceMaxFilter > -1d) {
                Criteria minPriceCriteria = new Criteria(LogicalOperator.AND, "p.unit_price", CriteriaOperator.LESS_OR_EQUAL, priceMaxFilter);
                criteria.add(minPriceCriteria);
            } else {
                return ResponseEntity.status(400).body("?priceMaxFilter should be greater or equal to 0");
            }
        }

        if (page != null) {
            if (pageSize != null) {
                ingredients = ingredientDAO.getAllByCriteria(criteria, page, pageSize);
            } else {
                return ResponseEntity.status(400).body("?page should be given with ?pageSize");
            }
        } else {
            ingredients = ingredientDAO.getAllByCriteria(criteria);
        }

        ingredientDTOSs = ingredients.stream().map(ingredientMapper::toDTO).toList();

        return ResponseEntity.ok(ingredientDTOSs);
    }

    @PostMapping
    public ResponseEntity<?> createIngredients(@RequestBody List<IngredientDTO> ingredientDTOs) {
        List<Ingredient> ingredients = ingredientDTOs.stream().map(ingredientMapper::toEntity).toList();

        ingredientDAO.saveAll(ingredients);

        return ResponseEntity.ok(ingredients);
    }

    @PutMapping
    public ResponseEntity<?> updateIngredients(@RequestBody List<IngredientDTO> ingredientDTOs) {
        List<Ingredient> ingredients = ingredientDTOs.stream().map(ingredientMapper::toEntity).toList();

        ingredientDAO.saveAll(ingredients);

        return ResponseEntity.ok(ingredients);
    }

    @PutMapping("/{ingredientId}/prices")
    public ResponseEntity<?> setPrices(@PathVariable String ingredientId, @RequestBody List<Price> prices) {
        priceDAO.saveAllByIngredientId(ingredientId, prices);

        return ResponseEntity.ok(prices);
    }
}

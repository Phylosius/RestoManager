package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.Repository.DishNotFoundException;
import hei.phylosius.restomanager.Repository.IngredientNotFoundException;
import hei.phylosius.restomanager.Service.DishService;
import hei.phylosius.restomanager.dto.MakeUpRestCreation;
import hei.phylosius.restomanager.dto.DishRest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/dishes")
public class DishController {

    private DishService dishService;

    @GetMapping
    public ResponseEntity<?> getDishes() {
        List<DishRest> dishes = dishService.getDishRests();

        return ResponseEntity.ok(dishes);
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> addIngredients(
            @PathVariable String id,
            @RequestBody List<MakeUpRestCreation> ingredientCreations
    ) {
        try {
            dishService.addIngredients(id, ingredientCreations);
            return ResponseEntity.ok(ingredientCreations);
        } catch (IngredientNotFoundException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (DishNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
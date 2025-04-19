package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.Repository.DishNotFoundException;
import hei.phylosius.restomanager.Repository.IngredientNotFoundException;
import hei.phylosius.restomanager.Service.DishService;
import hei.phylosius.restomanager.dto.CreateDishIngredientRest;
import hei.phylosius.restomanager.dto.ErrorResponseRest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/dishes")
public class DishRestController {

    private final DishService dishService;

    @GetMapping("")
    public ResponseEntity<?> getDishes(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize)
    {
        try {
            return ResponseEntity.ok(dishService.getDishes(page, pageSize));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponseRest(e));
        }
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> updateIngredients(
            @PathVariable Integer id,
            @RequestBody List<CreateDishIngredientRest> ingredients
    ) {
        try {
            return ResponseEntity.ok(dishService.updateIngredients(id, ingredients));
        } catch (DishNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorResponseRest(e));
        } catch (IngredientNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(403).body(new ErrorResponseRest(e));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponseRest(e));
        }
    }
}

package hei.phylosius.restomanager.controller;

import hei.phylosius.restomanager.dao.IngredientDAO;
import hei.phylosius.restomanager.mappers.IngredientMapper;
import hei.phylosius.restomanager.model.Ingredient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientRestController {

    private IngredientDAO ingredientDAO;

    public IngredientRestController(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }

    @GetMapping
    public ResponseEntity<?> getIngredients(IngredientMapper mapper, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int pageSize) {
        List<Ingredient> ingredients = ingredientDAO.getAllByCriteria(List.of(), page, pageSize);

        return ResponseEntity.ok(ingredients.stream().map(mapper::toDTO).toList());
    }
}

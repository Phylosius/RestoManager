package hei.phylosius.restomanager.controller;

import hei.phylosius.restomanager.dao.IngredientDAO;
import hei.phylosius.restomanager.dto.IngredientDTO;
import hei.phylosius.restomanager.mappers.IngredientMapper;
import hei.phylosius.restomanager.model.Ingredient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientRestController {

    private final IngredientMapper ingredientMapper;
    private IngredientDAO ingredientDAO;

    public IngredientRestController(IngredientDAO ingredientDAO, IngredientMapper ingredientMapper) {
        this.ingredientDAO = ingredientDAO;
        this.ingredientMapper = ingredientMapper;
    }

    @GetMapping
    public ResponseEntity<?> getIngredients(IngredientMapper mapper, @RequestParam Integer page, @RequestParam Integer pageSize) {
        List<IngredientDTO> ingredientDTOSs;
        List<Ingredient> ingredients = new ArrayList<>();

        if (page != null) {
            if (pageSize != null) {
                ingredients = ingredientDAO.getAll(page, pageSize);
            } else {
                ResponseEntity.status(400).body("?page should be given with ?pageSize");
            }
        } else {
            ingredients = ingredientDAO.getAll();
        }

        ingredientDTOSs = ingredients.stream().map(ingredientMapper::toDTO).toList();

        return ResponseEntity.ok(ingredientDTOSs);
    }
}

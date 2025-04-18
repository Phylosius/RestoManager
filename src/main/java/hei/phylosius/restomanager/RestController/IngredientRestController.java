package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.Service.IngredientService;
import hei.phylosius.restomanager.dto.ErrorResponseRest;
import hei.phylosius.restomanager.dto.PriceRest;
import hei.phylosius.restomanager.dto.StockMovementRest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientRestController {

    private final IngredientService ingredientService;

    public IngredientRestController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("")
    public ResponseEntity<?> getIngredients(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize
    ) {
        try {
            return ResponseEntity.ok(ingredientService.getAllIngredients(page, pageSize));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponseRest(e));
        }
    }

    @PutMapping("/{id}/prices")
    public ResponseEntity<?> updatePrices(
            @PathVariable Integer id,
            @RequestBody List<PriceRest> prices
    ){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PutMapping("/{id}/stockMovements")
    public ResponseEntity<?> addStockMovements(
            @PathVariable Integer id,
            @RequestBody List<StockMovementRest> stockMovements
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}

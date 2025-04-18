package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.dto.PriceRest;
import hei.phylosius.restomanager.dto.StockMovementRest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientRestController {

    @GetMapping("")
    public ResponseEntity<?> getIngredients(
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int pageSize
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
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

package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.dto.DishOrderRestUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

    @GetMapping("/{reference}")
    public ResponseEntity<?> getOrderInfo(
            @PathVariable String reference
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/{reference}/dishes")
    public ResponseEntity<?> updateDishes(
            @PathVariable String reference,
            @RequestBody List<DishOrderRestUpdate> dishes
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PutMapping("/{reference}/dishes/{dishId}")
    public ResponseEntity<?> updateDishStatus(
            @PathVariable String reference,
            @PathVariable String dishId,
            @RequestBody DishOrderRestUpdate update
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

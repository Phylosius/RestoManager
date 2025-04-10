package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.Repository.OrderNotFoundException;
import hei.phylosius.restomanager.Service.OrderService;
import hei.phylosius.restomanager.dto.DishOrderRestUpdate;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderRestController {

    private OrderService orderService;

    @GetMapping("/{reference}")
    public ResponseEntity<?> getOrderInfo(
            @PathVariable String reference
    ) {
        try {
            return ResponseEntity.ok(orderService.getOrderInfo(reference));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{reference}/dishes")
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

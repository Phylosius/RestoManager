package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.Repository.DishNotFoundException;
import hei.phylosius.restomanager.Repository.OrderNotFoundException;
import hei.phylosius.restomanager.Service.OrderService;
import hei.phylosius.restomanager.dto.DishOrderRestUpdate;
import hei.phylosius.restomanager.dto.UpdateOrderRest;
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
            return ResponseEntity.ok(orderService.getOrderInfoByReference(reference));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/{reference}")
    public ResponseEntity<?> addOrder(
            @PathVariable String reference
    ) {
        if (orderService.isReferenceInUse(reference)) {
            return ResponseEntity.status(400).body(String.format("reference %s is already in use", reference));
        }

        try {
            orderService.createEmptyOrder(reference);

            return ResponseEntity.status(200).body(orderService.getOrderInfoByReference(reference));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{reference}/dishes")
    public ResponseEntity<?> updateDishes(
            @PathVariable String reference,
            @RequestBody List<UpdateOrderRest> dishes
    ) {
        try {
            orderService.updateDishes(reference, dishes);

            return ResponseEntity.ok(orderService.getOrderInfoByReference(reference));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (DishNotFoundException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{reference}/dishes/{dishId}")
    public ResponseEntity<?> updateDishStatus(
            @PathVariable String reference,
            @PathVariable String dishId,
            @RequestBody DishOrderRestUpdate update
    ) {
        return ResponseEntity.status(403).body("Not implemented yet.");
    }
}

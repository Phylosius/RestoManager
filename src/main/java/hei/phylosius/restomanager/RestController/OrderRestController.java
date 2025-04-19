package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.Repository.DishNotFoundException;
import hei.phylosius.restomanager.Repository.OrderNotFoundException;
import hei.phylosius.restomanager.Service.OrderService;
import hei.phylosius.restomanager.dto.ObjectWithStatus;
import hei.phylosius.restomanager.dto.UpdateOrderRest;
import hei.phylosius.restomanager.model.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody UpdateOrderRest update
    ) {
        try {
            orderService.updateOrder(reference, update);

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
            @PathVariable Integer dishId,
            @RequestBody ObjectWithStatus statusO
    ) {
        OrderStatus status = statusO.getStatus();
        String orderId = orderService.getOrderId(reference);
        OrderStatus lastStatus = orderService.getLastOrderStatus(orderId, dishId.toString());

        if (status.isAfter(lastStatus)) {
            orderService.addDishOrderStatus(orderId, dishId.toString(), status);
            return ResponseEntity.ok(orderService.getOrderInfoByReference(reference));
        } else {
            return ResponseEntity.status(400).body(String.format("An dish's Order can't switch from %s to %s.", lastStatus, status));
        }
    }
}

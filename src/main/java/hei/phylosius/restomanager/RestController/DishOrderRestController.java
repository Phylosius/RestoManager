package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.Service.OrderStatusRecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/dishOrders")
public class DishOrderRestController {

    private final OrderStatusRecordService orderStatusRecordService;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize
    ) {
        try {
            return ResponseEntity.ok(orderStatusRecordService.getAll(page, pageSize));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}

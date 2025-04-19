package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.Service.salesService;
import hei.phylosius.restomanager.dto.DishSoldRest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesRestController {

    private final salesService salesService;

    public SalesRestController(salesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("")
    public ResponseEntity<?> getSales(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) LocalDateTime fromDate,
            @RequestParam(required = false) LocalDateTime toDate
            )
    {
        if (limit != null && limit < 1) {
            return ResponseEntity.badRequest().body("?limit must be greater than 0");
        }

        if (fromDate != null && toDate != null) {
            if (fromDate.isAfter(toDate)) {
                return ResponseEntity.badRequest().body("?fromDate must be before ?toDate");
            }
        }

        try {
            return ResponseEntity.ok(salesService.getDishSales(limit, fromDate, toDate));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}

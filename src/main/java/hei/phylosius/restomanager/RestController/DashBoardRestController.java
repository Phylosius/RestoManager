package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.Service.DashBoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
public class DashBoardRestController {

    private DashBoardService dashBoardService;

    @GetMapping("/bestSales")
    public ResponseEntity<?> getBestSales(@RequestParam(required = false) Integer X,
                                          @RequestParam(required = false) LocalDateTime startDate,
                                          @RequestParam(required = false) LocalDateTime endDate) {
        try {
            return ResponseEntity.ok(dashBoardService.getBestSales(X, startDate, endDate));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}

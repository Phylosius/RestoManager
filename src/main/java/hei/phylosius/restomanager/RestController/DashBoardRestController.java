package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.Repository.DishNotFoundException;
import hei.phylosius.restomanager.Service.DashBoardService;
import hei.phylosius.restomanager.dto.ProcessingTimeType;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/dishes/{id}/processingTime")
    public ResponseEntity<?> getProcessingTime(@PathVariable String id,
                                               @RequestParam(required = false, defaultValue = "S") String timeFormat,
                                               @RequestParam(required = false) ProcessingTimeType processingTimeType,
                                               @RequestParam(required = false) LocalDateTime startDate,
                                               @RequestParam(required = false) LocalDateTime endDate
                                               ) {
        try {
            return ResponseEntity.ok(dashBoardService.getProcessingTime(id, processingTimeType, timeFormat, startDate, endDate));
        } catch (DishNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}

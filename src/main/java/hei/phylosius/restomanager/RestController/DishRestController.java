package hei.phylosius.restomanager.RestController;

import hei.phylosius.restomanager.Service.DishService;
import hei.phylosius.restomanager.dto.ErrorResponseRest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/dishes")
public class DishRestController {

    private final DishService dishService;

    @GetMapping("")
    public ResponseEntity<?> getDishes(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize)
    {
        try {
            return ResponseEntity.ok(dishService.getDishes(page, pageSize));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponseRest(e));
        }
    }
}

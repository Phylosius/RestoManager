package hei.phylosius.restomanager.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthRestController {

    @GetMapping("/ping")
    String pingPong(){
        return "pong";
    }
}

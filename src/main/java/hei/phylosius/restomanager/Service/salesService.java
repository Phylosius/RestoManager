package hei.phylosius.restomanager.Service;

import hei.phylosius.restomanager.Repository.DashBoardDAO;
import hei.phylosius.restomanager.dto.DishSoldRest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Component
public class salesService {

    private final DashBoardDAO dashBoardDAO;

    public List<DishSoldRest> getDishSales(Integer limit, LocalDateTime start, LocalDateTime end) {
        return dashBoardDAO.getBestSales(limit, start, end);
    }
}

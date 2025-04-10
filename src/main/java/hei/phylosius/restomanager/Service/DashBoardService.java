package hei.phylosius.restomanager.Service;


import hei.phylosius.restomanager.Repository.DashBoardDAO;
import hei.phylosius.restomanager.dto.DishSaleRest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class DashBoardService {

    private DashBoardDAO dashBoardDAO;

    public List<DishSaleRest> getBestSales(Integer limit, LocalDateTime startDate, LocalDateTime endDate) {
        return dashBoardDAO.getBestSales(limit, startDate, endDate);
    }
}

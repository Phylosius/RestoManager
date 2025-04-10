package hei.phylosius.restomanager.Service;


import hei.phylosius.restomanager.Repository.DashBoardDAO;
import hei.phylosius.restomanager.dto.DishProcessingTimeRest;
import hei.phylosius.restomanager.dto.DishSaleRest;
import hei.phylosius.restomanager.dto.ProcessingTimeType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class DashBoardService {

    private DashBoardDAO dashBoardDAO;

    public DishProcessingTimeRest getProcessingTime(String dishId, ProcessingTimeType processingTimeType, LocalDateTime startDate, LocalDateTime endDate) {
        return dashBoardDAO.getProcessingTime(dishId, processingTimeType, startDate, endDate);
    }

    public List<DishSaleRest> getBestSales(Integer limit, LocalDateTime startDate, LocalDateTime endDate) {
        return dashBoardDAO.getBestSales(limit, startDate, endDate);
    }
}

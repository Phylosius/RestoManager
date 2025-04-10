package hei.phylosius.restomanager.Service;


import hei.phylosius.restomanager.Repository.DishSaleDAO;
import hei.phylosius.restomanager.dto.DishSaleRest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class DishSaleService {

    private DishSaleDAO dishSaleDAO;

    public List<DishSaleRest> getBestSales(Integer limit, LocalDateTime startDate, LocalDateTime endDate) {
        return dishSaleDAO.getBestSales(limit, startDate, endDate);
    }
}

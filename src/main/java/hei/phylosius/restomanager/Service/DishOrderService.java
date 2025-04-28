package hei.phylosius.restomanager.Service;

import hei.phylosius.restomanager.Repository.DishOrderDAO;
import hei.phylosius.restomanager.model.DishOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DishOrderService {

    private final DishOrderDAO dishOrderDAO;

    public List<DishOrder> getAll(Integer page, Integer pageSize) {
        return dishOrderDAO.getAll(page, pageSize);
    }
}

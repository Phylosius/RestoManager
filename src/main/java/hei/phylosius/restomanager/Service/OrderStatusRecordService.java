package hei.phylosius.restomanager.Service;

import hei.phylosius.restomanager.Repository.OrderStatusRecordDAO;
import hei.phylosius.restomanager.model.OrderStatusRecord;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderStatusRecordService {

    private final OrderStatusRecordDAO orderStatusRecordDAO;

    public List<OrderStatusRecord> getAll(Integer page, Integer pageSize){
        return orderStatusRecordDAO.getAll(page, pageSize);
    }
}

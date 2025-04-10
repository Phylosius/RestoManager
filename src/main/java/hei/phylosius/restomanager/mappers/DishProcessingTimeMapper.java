package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.DishProcessingTimeRest;
import hei.phylosius.restomanager.dto.ProcessingTimeType;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DishProcessingTimeMapper {
    public DishProcessingTimeRest toDTO(ResultSet resultSet, ProcessingTimeType processingTimeType) throws SQLException {
        DishProcessingTimeRest dto = new DishProcessingTimeRest();

        dto.setProcessingTime(resultSet.getString("processing_time"));
        dto.setProcessingTimeType(processingTimeType);

        return dto;
    }
}

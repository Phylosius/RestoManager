package hei.phylosius.restomanager.mappers;

import hei.phylosius.restomanager.dto.DishSoldRest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Component
public class DishSoldMapper {

    public DishSoldRest toDTO(ResultSet res) throws SQLException {
        DishSoldRest dto = new DishSoldRest();

        dto.setDishIdentifier(Integer.parseInt(res.getString("dish_id")));
        dto.setDishName(res.getString("dish_name"));
        dto.setQuantitySold(res.getInt("sold_quantity"));

        return dto;
    }
}

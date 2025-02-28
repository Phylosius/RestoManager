package model;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Criteria {
    private LogicalOperator logicalOperator = LogicalOperator.AND;
    private String fieldName;
    private CriteriaOperator operator;
    private Object value;
    private Double distance;

    public Criteria(LogicalOperator logicalOperator, String fieldName, CriteriaOperator operator, Object value) {
        this.logicalOperator = logicalOperator;
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }

    public String getSqlValue() {

        String sqlLogicalOperator = logicalOperator.toString();
        String sqlOperator = switch (operator){
            case EQUAL:
                yield "%s = '%s'";
            case NOT_EQUAL :
                yield "%s != '%s'";
            case GREATER_THAN:
                yield "%s > %s";
            case LESS_THAN:
                yield "%s < %s";
            case NEAR: {
                if (value instanceof String) {
                    yield "%s ILIKE %%%s%%";
                } else if (value instanceof Double) {
                    yield "ABS(%s::FLOAT - %s::FLOAT) <= %s::FLOAT";
                } else if (value instanceof LocalDateTime) {
                    yield "ABS(EXTRACT(EPOCH FROM (%s - %s::TIMESTAMP))) <= %s";
                } else {
                    throw new IllegalArgumentException("NEAR operation of given value not supported");
                }
            }
            case ORDER_BY: {
                if (value instanceof OrderType){
                    yield "ORDER BY %s %s";
                } else {
                    throw new IllegalArgumentException("value  on ORDER_BY operation must be a OrderType");
                }
            }
        };

        String sql;
        if (operator != CriteriaOperator.ORDER_BY) {
            if (value instanceof LocalDateTime || value instanceof Double) {
                sql = String.format(" %s %s",
                        sqlLogicalOperator, String.format(sqlOperator, getFieldName(),
                                getValue() instanceof LocalDateTime ? Timestamp.valueOf(getValue().toString()) : getValue(),
                                getDistance()));
            } else {
                sql = String.format(" %s %s",
                        sqlLogicalOperator, String.format(sqlOperator, getFieldName(), getValue()));
            }
        } else {
            sql = String.format(" %s %s",
                    sqlLogicalOperator, String.format(getFieldName(), getValue()));
        }

        return sql;
    }
}

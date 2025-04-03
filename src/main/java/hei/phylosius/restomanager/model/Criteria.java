package hei.phylosius.restomanager.model;

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
        String sqlOperator;
        String finalValue = value.toString();

        if (value instanceof String || value instanceof LocalDateTime || value instanceof MovementType || value instanceof Unit) {
            Object _value = value instanceof LocalDateTime ? Timestamp.valueOf((LocalDateTime) value) : value;
            if (_value instanceof String) {
                String strValue = (String) _value;
                strValue = strValue.replace("'", "''");
                finalValue = String.format("'%s'", strValue);
            } else {
                finalValue = String.format("'%s'", _value);
            }
        }

        switch (operator) {
            case EQUAL:
                sqlOperator = "%s = %s";
                break;
            case NOT_EQUAL:
                sqlOperator = "%s != %s";
                break;
            case GREATER_THAN:
                sqlOperator = "%s > %s";
                break;
            case GREATER_OR_EQUAL:
                sqlOperator = "%s >= %s";
                break;
            case LESS_THAN:
                sqlOperator = "%s < %s";
                break;
            case LESS_OR_EQUAL:
                sqlOperator = "%s <= %s";
                break;
            case NEAR:
                if (value instanceof String) {
                    String strValue = (String) value;
                    strValue = strValue.replace("'", "''");
                    sqlOperator = "%s ILIKE '%%%s%%'";
                    finalValue = strValue;
                } else if (value instanceof Double) {
                    sqlOperator = "ABS(%s::FLOAT - %s::FLOAT) <= %s::FLOAT";
                } else if (value instanceof LocalDateTime) {
                    sqlOperator = "ABS(EXTRACT(EPOCH FROM (%s - %s::TIMESTAMP))) <= %s";
                } else {
                    throw new IllegalArgumentException("NEAR operation of given value not supported");
                }
                break;
            case ORDER_BY:
                if (value instanceof FilterOrderType) {
                    sqlOperator = "ORDER BY %s %s";
                } else {
                    throw new IllegalArgumentException("value on ORDER_BY operation must be a OrderType");
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }

        String sql;
        if (operator != CriteriaOperator.ORDER_BY) {
            if (value instanceof LocalDateTime || value instanceof Double) {
                sql = String.format(" %s %s",
                        sqlLogicalOperator, String.format(sqlOperator, getFieldName(),
                                finalValue,
                                getDistance()));
            } else {
                sql = String.format(" %s %s",
                        sqlLogicalOperator, String.format(sqlOperator, getFieldName(), finalValue));
            }
        } else {
            sql = String.format(" %s %s",
                    sqlLogicalOperator, String.format(getFieldName(), finalValue));
        }

        return sql;
    }
}

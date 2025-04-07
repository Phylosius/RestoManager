package hei.phylosius.restomanager.model;


public enum OrderStatus{
    CREATED,
    CONFIRMED,
    IN_PREPARATION,
    FINISHED,
    SERVED;

    public Boolean isAfter(OrderStatus status) {
        return this.ordinal() > status.ordinal();
    }
}

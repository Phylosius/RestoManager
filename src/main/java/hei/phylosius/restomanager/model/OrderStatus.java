package hei.phylosius.restomanager.model;


public enum OrderStatus{
    CREATED,
    CONFIRMED,
    IN_PROGRESS,
    FINISHED,
    DELIVERED;

    public Boolean isAfter(OrderStatus status) {
        return this.ordinal() > status.ordinal();
    }
}

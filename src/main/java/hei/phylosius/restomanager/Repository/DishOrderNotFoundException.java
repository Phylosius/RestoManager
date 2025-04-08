package hei.phylosius.restomanager.Repository;

public class DishOrderNotFoundException extends RuntimeException {
    public DishOrderNotFoundException(String message) {
        super(message);
    }
}

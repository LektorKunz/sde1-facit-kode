package library.logic;

public interface Material {
    String getTitle();
    boolean isAvailable();
    void borrow();
    void returnItem();
}

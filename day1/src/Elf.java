import java.util.ArrayList;
import java.util.List;

public class Elf implements AutoCloseable {

    List<Integer> foodItems;

    Integer id;

    static Integer elves = 0;

    public Elf() {
        foodItems = new ArrayList<>();
        id = elves++;
    }

    public void close() {
        --elves;
    }

    public void addFoodItem(Integer item) {
        foodItems.add(item);
    }

    public Integer getTotalCalories() {
        Integer total = 0;
        for (Integer item : foodItems) {
            total += item;
        }
        return total;
    }

    public String label() {
        return "Elf #" + id;
    }

    public Integer id() {
        return id;
    }
}

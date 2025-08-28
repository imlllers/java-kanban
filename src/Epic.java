import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public String getTitle() {
        return super.getTitle();
    }

    public String getDescription() {
        return super.getDescription();
    }

    public int getId() {
        return super.getId();
    }

    public Status getStatus() {
        return super.getStatus();
    }
}

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String title, String description, int epicId) {
        super(title, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
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

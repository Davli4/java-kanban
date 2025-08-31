
public class SubTask extends Task {
    private int epicId;
    private TaskStatus status;

    public SubTask() {
        super();
        this.status = TaskStatus.NEW;
    }

    public SubTask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.status = TaskStatus.NEW;
    }

    public int getEpicId() {
        return epicId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + status +
                ", epicId=" + epicId +
                '}';
    }
}
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package model;

public class SubTask extends Task {
    private int epicId;

    public SubTask() {
    }

    public SubTask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return this.epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public String toString() {
        int var10000 = this.getId();
        return "SubTask{id=" + var10000 + ", name='" + this.getName() + "', description='" + this.getDescription() + "', status=" + String.valueOf(this.getStatus()) + ", epicId=" + this.epicId + "}";
    }
}

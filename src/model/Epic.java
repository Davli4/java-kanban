//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds = new ArrayList();

    public Epic() {
    }

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Integer> getSubtaskIds() {
        return this.subtaskIds;
    }

    public void setSubtaskIds(ArrayList<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
    }

    public void addSubtaskId(int subtaskId) {
        this.subtaskIds.add(subtaskId);
    }

    public void removeSubtaskId(int subtaskId) {
        this.subtaskIds.remove(subtaskId);
    }

    public String toString() {
        int var10000 = this.getId();
        return "Epic{id=" + var10000 + ", name='" + this.getName() + "', description='" + this.getDescription() + "', status=" + String.valueOf(this.getStatus()) + ", subtaskIds=" + String.valueOf(this.subtaskIds) + "}";
    }
}

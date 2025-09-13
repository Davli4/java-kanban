//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package manager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import model.Task;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_HISTORY_SIZE = 10;
    private final LinkedList<Task> history = new LinkedList();

    public void add(Task task) {
        if (task != null) {
            this.history.add(task);
            if (this.history.size() > 10) {
                this.history.removeFirst();
            }

        }
    }

    public List<Task> getHistory() {
        return new ArrayList(this.history);
    }
}

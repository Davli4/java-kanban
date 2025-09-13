//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package manager;

import java.util.List;
import model.Task;

public interface HistoryManager {
    void add(Task var1);

    List<Task> getHistory();
}

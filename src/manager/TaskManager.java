//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package manager;

import java.util.List;
import model.Epic;
import model.SubTask;
import model.Task;

public interface TaskManager {
    void addTask(Task var1);

    List<Task> getAllTasks();

    void removeAllTasks();

    Task getTaskById(int var1);

    void updateTask(Task var1);

    void removeTaskById(int var1);

    void addEpic(Epic var1);

    List<Epic> getAllEpics();

    void removeAllEpics();

    Epic getEpicById(int var1);

    void updateEpic(Epic var1);

    void removeEpicById(int var1);

    void addSubTask(SubTask var1);

    List<SubTask> getAllSubTasks();

    void removeAllSubTasks();

    SubTask getSubTaskById(int var1);

    void updateSubTask(SubTask var1);

    void removeSubTaskById(int var1);

    List<SubTask> getSubTasksByEpicId(int var1);

    List<Task> getHistory();
}

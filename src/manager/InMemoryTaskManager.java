//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap();
    private final HashMap<Integer, Epic> epics = new HashMap();
    private final HashMap<Integer, SubTask> subtasks = new HashMap();
    private final HistoryManager historyManager;
    private int nextId = 1;

    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistory();
    }

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    public void addTask(Task task) {
        if (task != null) {
            task.setId(this.generateId());
            this.tasks.put(task.getId(), task);
        }
    }

    public List<Task> getAllTasks() {
        return new ArrayList(this.tasks.values());
    }

    public void removeAllTasks() {
        this.tasks.clear();
    }

    public Task getTaskById(int id) {
        Task task = (Task)this.tasks.get(id);
        if (task != null) {
            this.historyManager.add(task);
        }

        return task;
    }

    public void updateTask(Task task) {
        if (task != null && this.tasks.containsKey(task.getId())) {
            this.tasks.put(task.getId(), task);
        }

    }

    public void removeTaskById(int id) {
        this.tasks.remove(id);
    }

    public void addEpic(Epic epic) {
        if (epic != null) {
            epic.setId(this.generateId());
            this.epics.put(epic.getId(), epic);
        }
    }

    public List<Epic> getAllEpics() {
        return new ArrayList(this.epics.values());
    }

    public void removeAllEpics() {
        for(Epic epic : this.epics.values()) {
            for(Integer subtaskId : epic.getSubtaskIds()) {
                this.subtasks.remove(subtaskId);
            }
        }

        this.epics.clear();
    }

    public Epic getEpicById(int id) {
        Epic epic = (Epic)this.epics.get(id);
        if (epic != null) {
            this.historyManager.add(epic);
        }

        return epic;
    }

    public void updateEpic(Epic epic) {
        if (epic != null && this.epics.containsKey(epic.getId())) {
            this.epics.put(epic.getId(), epic);
            this.updateEpicStatus(epic);
        }

    }

    public void removeEpicById(int id) {
        Epic epic = (Epic)this.epics.get(id);
        if (epic != null) {
            for(Integer subtaskId : epic.getSubtaskIds()) {
                this.subtasks.remove(subtaskId);
            }

            this.epics.remove(id);
        }

    }

    public void addSubTask(SubTask subTask) {
        if (subTask != null) {
            Epic epic = (Epic)this.epics.get(subTask.getEpicId());
            if (epic != null) {
                subTask.setId(this.generateId());
                this.subtasks.put(subTask.getId(), subTask);
                epic.addSubtaskId(subTask.getId());
                this.updateEpicStatus(epic);
            }
        }
    }

    public List<SubTask> getAllSubTasks() {
        return new ArrayList(this.subtasks.values());
    }

    public void removeAllSubTasks() {
        for(Epic epic : this.epics.values()) {
            epic.getSubtaskIds().clear();
            this.updateEpicStatus(epic);
        }

        this.subtasks.clear();
    }

    public SubTask getSubTaskById(int id) {
        SubTask subTask = (SubTask)this.subtasks.get(id);
        if (subTask != null) {
            this.historyManager.add(subTask);
        }

        return subTask;
    }

    public void updateSubTask(SubTask subTask) {
        if (subTask != null && this.subtasks.containsKey(subTask.getId())) {
            this.subtasks.put(subTask.getId(), subTask);
            Epic epic = (Epic)this.epics.get(subTask.getEpicId());
            if (epic != null) {
                this.updateEpicStatus(epic);
            }
        }

    }

    public void removeSubTaskById(int id) {
        SubTask subTask = (SubTask)this.subtasks.get(id);
        if (subTask != null) {
            Epic epic = (Epic)this.epics.get(subTask.getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(id);
                this.updateEpicStatus(epic);
            }

            this.subtasks.remove(id);
        }

    }

    public List<SubTask> getSubTasksByEpicId(int epicId) {
        Epic epic = (Epic)this.epics.get(epicId);
        if (epic == null) {
            return new ArrayList();
        } else {
            List<SubTask> result = new ArrayList();

            for(Integer subtaskId : epic.getSubtaskIds()) {
                SubTask subTask = (SubTask)this.subtasks.get(subtaskId);
                if (subTask != null) {
                    result.add(subTask);
                }
            }

            return result;
        }
    }

    public List<Task> getHistory() {
        return this.historyManager.getHistory();
    }

    private int generateId() {
        return this.nextId++;
    }

    private void updateEpicStatus(Epic epic) {
        if (epic != null) {
            List<Integer> subtaskIds = epic.getSubtaskIds();
            if (subtaskIds.isEmpty()) {
                epic.setStatus(TaskStatus.NEW);
            } else {
                boolean allDONE = true;
                boolean allNEW = true;

                for(Integer subtaskId : subtaskIds) {
                    SubTask subTask = (SubTask)this.subtasks.get(subtaskId);
                    if (subTask != null) {
                        if (subTask.getStatus() != TaskStatus.DONE) {
                            allDONE = false;
                        }

                        if (subTask.getStatus() != TaskStatus.NEW) {
                            allNEW = false;
                        }
                    }
                }

                if (allDONE) {
                    epic.setStatus(TaskStatus.DONE);
                } else if (allNEW) {
                    epic.setStatus(TaskStatus.NEW);
                } else {
                    epic.setStatus(TaskStatus.IN_PROGRESS);
                }

            }
        }
    }
}

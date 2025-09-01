import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



// Я понял свою ошибку
public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subtasks = new HashMap<>();
    private int nextId = 1;

    private int generateId() {
        return nextId++;
    }

    public void addTask(Task task) {
        if (task == null) return;
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public void updateTask(Task task) {
        if (task != null && tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void addEpic(Epic epic) {
        if (epic == null) return;
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
        epics.clear();
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public void updateEpic(Epic epic) {
        if (epic != null && epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            updateEpicStatus(epic);
        }
    }

    public void removeEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        }
    }

    public void addSubTask(SubTask subTask) {
        if (subTask == null) return;

        Epic epic = epics.get(subTask.getEpicId());
        if (epic == null) return;

        subTask.setId(generateId());
        subtasks.put(subTask.getId(), subTask);
        epic.addSubtaskId(subTask.getId());
        updateEpicStatus(epic);
    }

    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void removeAllSubTasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateEpicStatus(epic);
        }
        subtasks.clear();
    }

    public SubTask getSubTaskById(int id) {
        return subtasks.get(id);
    }

    public void updateSubTask(SubTask subTask) {
        if (subTask != null && subtasks.containsKey(subTask.getId())) {
            subtasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicId());
            if (epic != null) {
                updateEpicStatus(epic);
            }
        }
    }

    public void removeSubTaskById(int id) {
        SubTask subTask = subtasks.get(id);
        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(id);
                updateEpicStatus(epic);
            }
            subtasks.remove(id);
        }
    }

    public List<SubTask> getSubTasksByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return new ArrayList<>();
        }
        List<SubTask> result = new ArrayList<>();
        for (Integer subtaskId : epic.getSubtaskIds()) {
            SubTask subTask = subtasks.get(subtaskId);
            if (subTask != null) {
                result.add(subTask);
            }
        }
        return result;
    }

    private void updateEpicStatus(Epic epic) {
        if (epic == null) return;
        List<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtaskIds.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        boolean allDONNE = true;
        boolean allNEW = true;
        for (Integer subtaskId : subtaskIds) {
            SubTask subTask = subtasks.get(subtaskId);
            if (subTask != null) {
                if (subTask.getStatus() != TaskStatus.DONE) {
                    allDONNE = false;
                }
                if (subTask.getStatus() != TaskStatus.NEW) {
                    allNEW = false;
                }
            }
        }

        if (allDONNE) {
            epic.setStatus(TaskStatus.DONE);
        } else if (allNEW) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package test;

import java.util.List;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        this.taskManager = new InMemoryTaskManager();
    }

    @Test
    void shouldCreateTask() {
        Task task = new Task("Test Task", "Test Description");
        this.taskManager.addTask(task);
        Task savedTask = this.taskManager.getTaskById(task.getId());
        Assertions.assertNotNull(savedTask);
        Assertions.assertEquals(task, savedTask);
    }

    @Test
    void shouldReturnNullForNonExistentTask() {
        Assertions.assertNull(this.taskManager.getTaskById(999));
    }

    @Test
    void shouldUpdateTask() {
        Task task = new Task("Original", "Description");
        this.taskManager.addTask(task);
        Task updated = new Task("Updated", "New Description");
        updated.setId(task.getId());
        this.taskManager.updateTask(updated);
        Assertions.assertEquals("Updated", this.taskManager.getTaskById(task.getId()).getName());
    }

    @Test
    void shouldRemoveTask() {
        Task task = new Task("To remove", "Description");
        this.taskManager.addTask(task);
        this.taskManager.removeTaskById(task.getId());
        Assertions.assertNull(this.taskManager.getTaskById(task.getId()));
    }

    @Test
    void shouldRemoveAllTasks() {
        this.taskManager.addTask(new Task("Task 1", "Desc"));
        this.taskManager.addTask(new Task("Task 2", "Desc"));
        this.taskManager.removeAllTasks();
        Assertions.assertTrue(this.taskManager.getAllTasks().isEmpty());
    }

    @Test
    void shouldCreateEpic() {
        Epic epic = new Epic("Test Epic", "Test Description");
        this.taskManager.addEpic(epic);
        Epic savedEpic = this.taskManager.getEpicById(epic.getId());
        Assertions.assertNotNull(savedEpic);
        Assertions.assertEquals(epic, savedEpic);
        Assertions.assertEquals(TaskStatus.NEW, savedEpic.getStatus());
    }

    @Test
    void shouldCreateSubTask() {
        Epic epic = new Epic("Parent Epic", "Description");
        this.taskManager.addEpic(epic);
        SubTask subTask = new SubTask("Test SubTask", "Test Description", epic.getId());
        this.taskManager.addSubTask(subTask);
        SubTask savedSubTask = this.taskManager.getSubTaskById(subTask.getId());
        Assertions.assertNotNull(savedSubTask);
        Assertions.assertEquals(subTask, savedSubTask);
        Assertions.assertEquals(epic.getId(), savedSubTask.getEpicId());
    }

    @Test
    void shouldNotAddSubTaskWithInvalidEpicId() {
        SubTask subTask = new SubTask("SubTask", "Description", 999);
        this.taskManager.addSubTask(subTask);
        Assertions.assertTrue(this.taskManager.getAllSubTasks().isEmpty());
    }

    @Test
    void shouldUpdateEpicStatusWhenSubTasksChange() {
        Epic epic = new Epic("Epic", "Description");
        this.taskManager.addEpic(epic);
        SubTask subTask = new SubTask("SubTask", "Description", epic.getId());
        subTask.setStatus(TaskStatus.DONE);
        this.taskManager.addSubTask(subTask);
        Epic savedEpic = this.taskManager.getEpicById(epic.getId());
        Assertions.assertEquals(TaskStatus.DONE, savedEpic.getStatus());
    }

    @Test
    void shouldNotAddNullTasks() {
        this.taskManager.addTask((Task)null);
        this.taskManager.addEpic((Epic)null);
        this.taskManager.addSubTask((SubTask)null);
        Assertions.assertTrue(this.taskManager.getAllTasks().isEmpty());
        Assertions.assertTrue(this.taskManager.getAllEpics().isEmpty());
        Assertions.assertTrue(this.taskManager.getAllSubTasks().isEmpty());
    }

    @Test
    void shouldGenerateUniqueIds() {
        Task task1 = new Task("Task 1", "Description");
        Task task2 = new Task("Task 2", "Description");
        this.taskManager.addTask(task1);
        this.taskManager.addTask(task2);
        Assertions.assertNotEquals(task1.getId(), task2.getId());
    }

    @Test
    void shouldAddTasksToHistory() {
        Task task = new Task("Task", "Description");
        this.taskManager.addTask(task);
        this.taskManager.getTaskById(task.getId());
        Assertions.assertEquals(1, this.taskManager.getHistory().size());
        Assertions.assertEquals(task, this.taskManager.getHistory().get(0));
    }

    @Test
    void shouldReturnAllTasks() {
        Task task = new Task("Task", "Description");
        Epic epic = new Epic("Epic", "Description");
        this.taskManager.addTask(task);
        this.taskManager.addEpic(epic);
        List<Task> tasks = this.taskManager.getAllTasks();
        List<Epic> epics = this.taskManager.getAllEpics();
        Assertions.assertEquals(1, tasks.size());
        Assertions.assertEquals(1, epics.size());
        Assertions.assertEquals(task, tasks.get(0));
        Assertions.assertEquals(epic, epics.get(0));
    }

    @Test
    void shouldRemoveAllEpicsAndSubTasks() {
        Epic epic = new Epic("Epic", "Description");
        this.taskManager.addEpic(epic);
        SubTask subTask = new SubTask("SubTask", "Description", epic.getId());
        this.taskManager.addSubTask(subTask);
        this.taskManager.removeAllEpics();
        Assertions.assertTrue(this.taskManager.getAllEpics().isEmpty());
        Assertions.assertTrue(this.taskManager.getAllSubTasks().isEmpty());
    }

    @Test
    void shouldRemoveAllSubTasksAndUpdateEpics() {
        Epic epic = new Epic("Epic", "Description");
        this.taskManager.addEpic(epic);
        SubTask subTask = new SubTask("SubTask", "Description", epic.getId());
        this.taskManager.addSubTask(subTask);
        this.taskManager.removeAllSubTasks();
        Assertions.assertTrue(this.taskManager.getAllSubTasks().isEmpty());
        Epic savedEpic = this.taskManager.getEpicById(epic.getId());
        Assertions.assertTrue(savedEpic.getSubtaskIds().isEmpty());
        Assertions.assertEquals(TaskStatus.NEW, savedEpic.getStatus());
    }

    @Test
    void shouldGetSubTasksByEpicId() {
        Epic epic = new Epic("Epic", "Description");
        this.taskManager.addEpic(epic);
        SubTask subTask1 = new SubTask("SubTask 1", "Description", epic.getId());
        SubTask subTask2 = new SubTask("SubTask 2", "Description", epic.getId());
        this.taskManager.addSubTask(subTask1);
        this.taskManager.addSubTask(subTask2);
        List<SubTask> epicSubTasks = this.taskManager.getSubTasksByEpicId(epic.getId());
        Assertions.assertEquals(2, epicSubTasks.size());
        Assertions.assertTrue(epicSubTasks.contains(subTask1));
        Assertions.assertTrue(epicSubTasks.contains(subTask2));
    }

    @Test
    void shouldReturnEmptyListForNonExistentEpicSubTasks() {
        List<SubTask> subTasks = this.taskManager.getSubTasksByEpicId(999);
        Assertions.assertTrue(subTasks.isEmpty());
    }

    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task1 = new Task("Task 1", "Description");
        Task task2 = new Task("Task 2", "Different description");
        task1.setId(1);
        task2.setId(1);
        Assertions.assertEquals(task1, task2);
        Assertions.assertEquals(task1.hashCode(), task2.hashCode());
    }

    @Test
    void shouldHandleMultipleTaskTypesInHistory() {
        Task task = new Task("Task", "SomethingTask");
        Epic epic = new Epic("Epic", "SomethingEpic");
        this.taskManager.addTask(task);
        this.taskManager.addEpic(epic);
        this.taskManager.getTaskById(task.getId());
        this.taskManager.getEpicById(epic.getId());
        List<Task> history = this.taskManager.getHistory();
        Assertions.assertEquals(2, history.size());
        Assertions.assertTrue(history.contains(task));
        Assertions.assertTrue(history.contains(epic));
    }
}

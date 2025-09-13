import java.util.HashMap;
import java.util.Scanner;


// Я решил реализваоть все методы с задачами в TaskManager
public class TaskManager {
    Scanner scanner = new Scanner(System.in);
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subtasks = new HashMap<>();
    private int nextId = 1;


    //дэфолт менюшка
    public void printMenu() {
        while (true) {
            System.out.println("\n МЕНЮ УПРАВЛЕНИЯ ЗАДАЧАМИ ");
            System.out.println("1. Добавить обычную задачу");
            System.out.println("2. Показать обычные задачи");
            System.out.println("3. Удалить все обычные задачи");
            System.out.println("4. Найти обычную задачу по id");
            System.out.println("5. Удалить обычную задачу по id");
            System.out.println("6. Добавить эпик");
            System.out.println("7. Показать все эпики");
            System.out.println("8. Удалить все эпики");
            System.out.println("9. Найти эпик по id");
            System.out.println("10. Удалить эпик по id");
            System.out.println("11. Добавить подзадачу");
            System.out.println("12. Показать все подзадачи");
            System.out.println("13. Удалить все подзадачи");
            System.out.println("14. Найти подзадачу по id");
            System.out.println("15. Удалить подзадачу по id");
            System.out.println("16. Показать подзадачи эпика");
            System.out.println("17. Изменить статус обычной задачи");
            System.out.println("18. Изменить статус подзадачи");
            System.out.println("0. Выход");

            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addTask(createTask()); break;
                case 2: displayAllTasks(); break;
                case 3: removeAllTasks(); break;
                case 4: searchAndDisplayTaskById(); break;
                case 5: removeTaskById(); break;
                case 6: addEpic(createEpic()); break;
                case 7: displayAllEpics(); break;
                case 8: removeAllEpics(); break;
                case 9: searchAndDisplayEpicById(); break;
                case 10: removeEpicById(); break;
                case 11: addSubTask(createSubTask()); break;
                case 12: displayAllSubTasks(); break;
                case 13: removeAllSubTasks(); break;
                case 14: searchAndDisplaySubTaskById(); break;
                case 15: removeSubTaskById(); break;
                case 16: displaySubTasksForEpic(); break;
                case 17: changeTaskStatus(); break;
                case 18: changeSubTaskStatus(); break;
                case 0: System.out.println("Выход из программы"); return;
                default: System.out.println("Неверный выбор. Попробуйте ещё разок");
            }
        }
    }



    //Методы для Task

    private Task createTask() {
        System.out.print("Введите название задачи: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание задачи: ");
        String description = scanner.nextLine();
        Task task = new Task(name, description);
        task.setId(nextId++);
        return task;
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
        System.out.println("Задача добавлена: " + task);
    }

    public void displayAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Обычных задач нет.");
            return;
        }
        System.out.println("Все обычные задачи:");
        for (Task task : tasks.values()) {
            System.out.println(task);
        }
    }

    public void removeAllTasks() {
        tasks.clear();
        System.out.println("Все обычные задачи удалены.");
    }

    public void searchAndDisplayTaskById() {
        System.out.print("Введите id задачи: ");
        int id = scanner.nextInt();
        Task task = tasks.get(id);
        if (task != null) {
            System.out.println("Найдена задача: " + task);
        } else {
            System.out.println("Задача с id " + id + " не найдена.");
        }
    }

    public void removeTaskById() {
        System.out.print("Введите id задачи для удаления: ");
        int id = scanner.nextInt();
        Task removed = tasks.remove(id);
        if (removed != null) {
            System.out.println("Задача удалена: " + removed);
        } else {
            System.out.println("Задача с id " + id + " не найдена.");
        }
    }


    //Методы для эпиков
    private Epic createEpic() {
        System.out.print("Введите название эпика: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание эпика: ");
        String description = scanner.nextLine();
        Epic epic = new Epic(name, description);
        epic.setId(nextId++);
        return epic;
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        System.out.println("Эпик добавлен: " + epic);
    }

    public void displayAllEpics() {
        if (epics.isEmpty()) {
            System.out.println("Эпиков нет.");
            return;
        }
        System.out.println("Все эпики:");
        for (Epic epic : epics.values()) {
            System.out.println(epic);
        }
    }

    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
        epics.clear();
        System.out.println("Все эпики и связанные подзадачи удалены.");
    }

    public void searchAndDisplayEpicById() {
        System.out.print("Введите id эпика: ");
        int id = scanner.nextInt();
        Epic epic = epics.get(id);
        if (epic != null) {
            System.out.println("Найден эпик: " + epic);
        } else {
            System.out.println("Эпик с id " + id + " не найдена.");
        }
    }

    public void removeEpicById() {
        System.out.print("Введите id эпика для удаления: ");
        int id = scanner.nextInt();
        Epic epic = epics.get(id);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
            System.out.println("Эпик и его подзадачи удалены: " + epic);
        } else {
            System.out.println("Эпик с id " + id + " не найден.");
        }
    }


    // Методы для sub tasks
    private SubTask createSubTask() {
        System.out.print("Введите название подзадачи: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание подзадачи: ");
        String description = scanner.nextLine();

        System.out.print("Введите id эпика для этой подзадачи: ");
        int epicId = scanner.nextInt();
        scanner.nextLine();

        if (!epics.containsKey(epicId)) {
            System.out.println("Эпик с id " + epicId + " не существует!");
            return null;
        }

        SubTask subTask = new SubTask(name, description, epicId);
        subTask.setId(nextId++);
        return subTask;
    }

    public void addSubTask(SubTask subTask) {
        if (subTask == null) return;

        subtasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            epic.addSubtaskId(subTask.getId());
            updateEpicStatus(epic);
        }
        System.out.println("Подзадача добавлена: " + subTask);
    }

    public void displayAllSubTasks() {
        if (subtasks.isEmpty()) {
            System.out.println("Подзадач нет.");
            return;
        }
        System.out.println("Все подзадачи:");
        for (SubTask subTask : subtasks.values()) {
            System.out.println(subTask);
        }
    }

    public void removeAllSubTasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateEpicStatus(epic);
        }
        subtasks.clear();
        System.out.println("Все подзадачи удалены.");
    }

    public void searchAndDisplaySubTaskById() {
        System.out.print("Введите id подзадачи: ");
        int id = scanner.nextInt();
        SubTask subTask = subtasks.get(id);
        if (subTask != null) {
            System.out.println("Найдена подзадача: " + subTask);
        } else {
            System.out.println("Подзадача с id " + id + " не найдена.");
        }
    }

    public void removeSubTaskById() {
        System.out.print("Введите id подзадачи для удаления: ");
        int id = scanner.nextInt();
        SubTask subTask = subtasks.get(id);
        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(id);
                updateEpicStatus(epic);
            }
            subtasks.remove(id);
            System.out.println("Подзадача удалена: " + subTask);
        } else {
            System.out.println("Подзадача с id " + id + " не найдена.");
        }
    }

    public void displaySubTasksForEpic() {
        System.out.print("Введите id эпика: ");
        int epicId = scanner.nextInt();
        Epic epic = epics.get(epicId);

        if (epic == null) {
            System.out.println("Эпик с id " + epicId + " не найден.");
            return;
        }
        if (epic.getSubtaskIds().isEmpty()) {
            System.out.println("У эпика " + epicId + " нет подзадач.");
            return;
        }

        System.out.println("Подзадачи эпика " + epicId + ":");
        for (Integer subtaskId : epic.getSubtaskIds()) {
            SubTask subTask = subtasks.get(subtaskId);
            if (subTask != null) {
                System.out.println(subTask);
            }
        }
    }

    private void updateEpicStatus(Epic epic) {
        if (epic.getSubtaskIds().isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        boolean allDone = true;
        boolean allNew = true;

        for (Integer subtaskId : epic.getSubtaskIds()) {
            SubTask subTask = subtasks.get(subtaskId);
            if (subTask != null) {
                if (subTask.getStatus() != TaskStatus.DONE) {
                    allDone = false;
                }
                if (subTask.getStatus() != TaskStatus.NEW) {
                    allNew = false;
                }
            }
        }

        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }



    //Ниже я решил реалитзовать смену статусов задчи и подзадачи
    public void changeTaskStatus() {
        System.out.print("Введите id обычной задачи: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Task task = tasks.get(id);
        if (task == null) {
            System.out.println("Задача с id " + id + " не найдена.");
            return;
        }

        System.out.println("Текущий статус: " + task.getStatus());
        System.out.println("Выберите новый статус:");
        System.out.println("1. NEW");
        System.out.println("2. IN_PROGRESS");
        System.out.println("3. DONE");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1: task.setStatus(TaskStatus.NEW); break;
            case 2: task.setStatus(TaskStatus.IN_PROGRESS); break;
            case 3: task.setStatus(TaskStatus.DONE); break;
            default: System.out.println("Неверный выбор статуса!"); return;
        }

        System.out.println("Статус задачи обновлен: " + task);
    }


    // В этом методе вместе с обновлением статуса подзадачи реализовано и измение статуса эпика
    public void changeSubTaskStatus() {
        System.out.print("Введите id подзадачи: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        SubTask subTask = subtasks.get(id);
        if (subTask == null) {
            System.out.println("Подзадача с id " + id + " не найдена.");
            return;
        }

        System.out.println("Текущий статус: " + subTask.getStatus());
        System.out.println("Выберите новый статус:");
        System.out.println("1. NEW");
        System.out.println("2. IN_PROGRESS");
        System.out.println("3. DONE");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1: subTask.setStatus(TaskStatus.NEW); break;
            case 2: subTask.setStatus(TaskStatus.IN_PROGRESS); break;
            case 3: subTask.setStatus(TaskStatus.DONE); break;
            default: System.out.println("Неверный выбор статуса!"); return;
        }

        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            updateEpicStatus(epic);
            System.out.println("Статус эпика " + epic.getId() + " обновлен: " + epic.getStatus());
        }

        System.out.println("Статус подзадачи обновлен: " + subTask);
    }

}
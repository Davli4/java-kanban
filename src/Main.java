<<<<<<< HEAD
import manager.Managers;
import manager.TaskManager;


public class Main {
    public static void main(String[] args) {
            TaskManager taskManager = Managers.getDefault();
    }
}
=======
public class Main {

    public static void main(String[] args) {
            TaskManager taskManager = new TaskManager();
            taskManager.printMenu();
    }
}
>>>>>>> 327efb13733a61add735fec0d0dde860a3690b1e

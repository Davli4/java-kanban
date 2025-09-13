//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package test;

import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    public void setUp() {
        this.historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void addTaskToHistory() {
        Task task = new Task("Task1", "Something");
        task.setId(1);
        this.historyManager.add(task);
        Assertions.assertEquals(1, this.historyManager.getHistory().size());
        Assertions.assertEquals(task, this.historyManager.getHistory().get(0));
    }

    @Test
    public void shouldNotNullTest() {
        this.historyManager.add((Task)null);
        Assertions.assertTrue(this.historyManager.getHistory().isEmpty());
    }

    @Test
    public void shouldLimitHistorySize() {
        for(int i = 1; i <= 15; ++i) {
            Task task = new Task("Task" + i, "Somthing about it");
            task.setId(i);
            this.historyManager.add(task);
        }

        Assertions.assertEquals(10, this.historyManager.getHistory().size());
        Assertions.assertEquals(6, ((Task)this.historyManager.getHistory().get(0)).getId());
    }
}

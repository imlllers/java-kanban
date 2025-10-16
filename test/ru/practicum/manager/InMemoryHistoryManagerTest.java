package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private TaskManager taskManager;
    private HistoryManager historyManager;

    @BeforeEach
    void setUp(){
        taskManager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void taskInHistoryShouldBeImmutable() {
        Task task = new Task("task title", "task description");
        taskManager.createTask(task);
        historyManager.add(task);
        task.setTitle("task title 2");
        task.setDescription("task description 2");
        taskManager.updateTask(task);
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size(),
                "История должна сохранять последнюю версию задачи");
        assertEquals("task description 2", historyManager.getHistory().get(0).getDescription(),
                "История должна сохранять обновленную версию задачи");
    }

    @Test
    void shouldSaveTasksInHistoryIsCorrectOrder() {
        Task task1 = new Task("task title 1", "task description 1");
        Task task2 = new Task("task title 2", "task description 2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        historyManager.add(task1);
        historyManager.add(task2);
        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "Размер истории должен быть 2");
        assertEquals("task title 1", history.get(0).getTitle(), "Первый элемент должен быть task title 1");
        assertEquals("task title 2", history.get(1).getTitle(), "Второй элемент должен быть task title 2");
    }

    @Test
    void shouldRemoveTasksFromHistory() {
        Task task1 = new Task("task title 1", "task description 1");
        Task task2 = new Task("task title 2", "task description 2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        historyManager.add(task1);
        historyManager.add(task2);
        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "Размер истории должен быть 2");
        historyManager.remove(task1.getId());
        history = historyManager.getHistory();
        assertEquals(1, history.size(), "Размер истории должен быть 1");
        assertEquals("task title 2", history.get(0).getTitle(), "Первая запись должна быть task title 2");
        historyManager.remove(task2.getId());
        history = historyManager.getHistory();
        assertEquals(0, history.size(), "Размер истории должен быть 0");
    }
}
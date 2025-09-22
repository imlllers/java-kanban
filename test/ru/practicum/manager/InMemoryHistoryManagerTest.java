package ru.practicum.manager;

import org.junit.jupiter.api.Test;
import ru.practicum.model.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    // задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных
    @Test
    void taskInHistoryShouldBeImmutable() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("task title", "task description");

        taskManager.createTask(task);
        historyManager.add(task);

        task.setTitle("task title 2");
        task.setDescription("task description 2");
        taskManager.updateTask(task);
        historyManager.add(task);

        assertEquals("task description", historyManager.getHistory().get(0).getDescription(),
                "История должна сохранять предыдущую версию задачи");
        assertEquals("task description 2", historyManager.getHistory().get(1).getDescription(),
                "История должна сохранять предыдущую версию задачи");
    }
}
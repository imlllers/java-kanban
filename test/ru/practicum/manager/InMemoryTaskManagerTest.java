package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    // InMemoryTaskManager добавляет задачи разного типа и может найти их по id;
    @Test
    void inMemoryTaskManagerShouldAddDifferentTasksAndFindThemById() {
        Task task = new Task("task title", "task description");
        taskManager.createTask(task);
        Epic epic = new Epic("epic title", "epic description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("subtask title", "subtask description", epic.getId());
        taskManager.createSubtask(subtask);

        assertEquals(task, taskManager.getTask(task.getId()), "Задачи должны находится по id");
        assertEquals(epic, taskManager.getEpic(epic.getId()), "Эпики должны находится по id");
        assertEquals(subtask, taskManager.getSubtask(subtask.getId()), "Подзадачи должны находится по id");
    }

    // задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
    @Test
    void tasksWithSetAndGeneratedIdShouldNotConflict() {
        Task task1 = new Task("task title 1", "task description 1");
        Task task2 = new Task("task title 2", "task description 2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        task2.setId(3);
        taskManager.updateTask(task2);

        assertNotEquals(task1, task2,
                "Задачи с заданным id и сгенерированным id не должны конфликтовать внутри менеджера");
    }

    // проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    @Test
    void taskShouldBeUnchangedAfterAddedToManager() {
        Task task1 = new Task("task title 1", "task description 1");
        taskManager.createTask(task1);

        Task sameTask = taskManager.getTask(task1.getId());

        assertEquals(task1.getId(), sameTask.getId(),
                "ID задачи не должен измениться после добавления в менеджер");
        assertEquals(task1.getTitle(), sameTask.getTitle(),
                "Название задачи не должно измениться после добавления в менеджер");
        assertEquals(task1.getDescription(), sameTask.getDescription(),
                "Описание задачи не должно измениться после добавления в менеджер");
        assertEquals(task1.getStatus(), sameTask.getStatus(),
                "Статус задачи не должен измениться после добавления в менеджер");

    }
}
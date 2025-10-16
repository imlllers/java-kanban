package ru.practicum.model;

import org.junit.jupiter.api.Test;
import ru.practicum.manager.InMemoryTaskManager;
import ru.practicum.manager.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    // Subtask нельзя сделать своим же эпиком
    @Test
    void subtaskShouldNotBeItsOwnEpic() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("epic title", "epic description");
        taskManager.createEpic(epic);

        Subtask subtask = new Subtask("subtask title", "subtask description", epic.getId());
        subtask.setId(epic.getId());
        taskManager.createSubtask(subtask);

        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Подзадача не может быть своим же эпиком");
    }

    // наследники класса Task равны друг другу, если равен их id
    @Test
    void subtasksWithSameIdShouldBeEqual() {
        Subtask subtask1 = new Subtask("title1", "description1", 1);
        Subtask subtask2 = new Subtask("title2", "description2", 1);
        assertEquals(subtask1, subtask2, "Подзадачи с одинаковыми Id должны быть равны");
    }
}
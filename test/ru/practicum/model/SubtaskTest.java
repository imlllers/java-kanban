package ru.practicum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    // Subtask нельзя сделать своим же эпиком
    @Test
    void subtaskShouldNotBeItsOwnEpic() {
        Subtask subtask = new Subtask("subtask title", "subtask description", 1);
        Epic epic = new Epic("epic title", "epic description");
        epic.setId(1);

        assertNotEquals(subtask.getId(), subtask.getEpicId(),
                "Подзадача не может быть своим же эпиком");
    }
}
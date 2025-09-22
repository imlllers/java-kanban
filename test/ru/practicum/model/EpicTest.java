package ru.practicum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    // Epic нельзя добавить в самого себя в виде подзадачи
    @Test
    void shouldNotAllowAddingEpicAsSubtaskToItself() {
        Epic epic = new Epic("epic", "description");
        epic.setId(1);
        epic.addSubtask(epic.getId());

        assertFalse(epic.getSubtaskIds().contains(epic.getId()),
                "Эпик нельзя добавить в виде подзадачи");
        assertEquals(0, epic.getSubtaskIds().size(),
                "Список подзадач эпика должен остаться пустым после попытки добавить самого себя");
    }
}
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

    // наследники класса Task равны друг другу, если равен их id
    @Test
    void epicsWithSameIdShouldBeEqual() {
        Epic epic1 = new Epic("title1", "description1");
        Epic epic2 = new Epic("title2", "description2");
        epic1.setId(1);
        epic2.setId(1);
        assertEquals(epic1, epic2, "Эпики с одинаковыми Id должны быть равны");
    }
}
package ru.practicum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    // экземпляры класса Task равны друг другу, если равен их id
    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task1 = new Task("title1", "description1");
        Task task2 = new Task("title2", "description2");
        task1.setId(1);
        task2.setId(1);
        assertEquals(task1, task2, "Задачи с одинаковыми Id должны быть равны");
    }

    // наследники класса Task равны друг другу, если равен их id
    @Test
    void subtasksWithSameIdShouldBeEqual() {
        Subtask subtask1 = new Subtask("title1", "description1", 1);
        Subtask subtask2 = new Subtask("title2", "description2", 1);
        assertEquals(subtask1, subtask2, "Подзадачи с одинаковыми Id должны быть равны");
    }

    @Test
    void epicsWithSameIdShouldBeEqual() {
        Epic epic1 = new Epic("title1", "description1");
        Epic epic2 = new Epic("title2", "description2");
        epic1.setId(1);
        epic2.setId(1);
        assertEquals(epic1, epic2, "Эпики с одинаковыми Id должны быть равны");
    }
}
package kanban.model;

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
}
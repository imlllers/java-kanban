package ru.practicum.manager;

import org.junit.jupiter.api.*;
import ru.practicum.model.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    private File tempFile;
    private FileBackedTaskManager manager;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("tasks", ".csv");
        tempFile.deleteOnExit();
        manager = new FileBackedTaskManager(tempFile);
    }

    @Test
    void shouldSaveAndLoadEmptyFile() {
        manager.save();

        FileBackedTaskManager loaded = FileBackedTaskManager.loadFromFile(tempFile);

        assertTrue(loaded.getAllTasks().isEmpty(), "Список задач должен быть пустым");
        assertTrue(loaded.getAllEpics().isEmpty(), "Список эпиков должен быть пустым");
        assertTrue(loaded.getAllSubtasks().isEmpty(), "Список сабтасков должен быть пустым");
    }

    @Test
    void shouldSaveAndLoadMultipleTasks() {
        Task task1 = new Task("task title 1", "task description 1");
        Task task2 = new Task("task title 2", "task description 2");
        manager.createTask(task1);
        manager.createTask(task2);

        Epic epic = new Epic("epic title 1", "epic description 1");
        manager.createEpic(epic);

        Subtask sub1 = new Subtask("sub title 1", "sub description 1", epic.getId());
        Subtask sub2 = new Subtask("sub title 2", "sub description 2", epic.getId());
        manager.createSubtask(sub1);
        manager.createSubtask(sub2);

        manager.save();

        FileBackedTaskManager loaded = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(2, loaded.getAllTasks().size(), "Должно быть 2 задачи");
        assertEquals(1, loaded.getAllEpics().size(), "Должен быть 1 эпик");
        assertEquals(2, loaded.getAllSubtasks().size(), "Должно быть 2 сабтаска");

        Task loadedTask = loaded.getAllTasks().getFirst();
        assertEquals("task title 1", loadedTask.getTitle());
        assertEquals(Status.NEW, loadedTask.getStatus());

        Epic loadedEpic = loaded.getAllEpics().getFirst();
        assertEquals("epic title 1", loadedEpic.getTitle());
        assertEquals(2, loadedEpic.getSubtaskIds().size(), "Эпик должен содержать 2 сабтаска");

        Subtask loadedSub = loaded.getAllSubtasks().getFirst();
        assertEquals(loadedEpic.getId(), loadedSub.getEpicId(), "Сабтаск должен ссылаться на свой эпик");
    }

    @Test
    void shouldRestoreCounterCorrectly() {
        Task task = new Task("task title", "task description");
        manager.createTask(task);
        manager.save();

        FileBackedTaskManager loaded = FileBackedTaskManager.loadFromFile(tempFile);

        int nextId = loaded.counter;
        assertTrue(nextId >= task.getId(), "Счётчик должен быть не меньше последнего ID");
    }
}

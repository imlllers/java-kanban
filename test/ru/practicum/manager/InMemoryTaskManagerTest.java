package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void shouldRemoveSubtaskAndNotLeaveIdInEpic() {
        Epic epic = new Epic("epic title", "epic description");
        int epicId = taskManager.createEpic(epic);
        Subtask sub1 = new Subtask("sub1", "desc1", epicId);
        Subtask sub2 = new Subtask("sub2", "desc2", epicId);
        int subId1 = taskManager.createSubtask(sub1);
        int subId2 = taskManager.createSubtask(sub2);
        taskManager.getSubtask(subId1);
        taskManager.getSubtask(subId2);
        taskManager.deleteSubtask(subId1);
        List<Subtask> remainingSubtasks = taskManager.getAllEpicSubtasks(epicId);
        assertEquals(1, remainingSubtasks.size(), "В эпике должен остаться только один id подзадачи");
        assertEquals(subId2, remainingSubtasks.get(0).getId(), "Оставшийся id должен быть корректным");
    }

    @Test
    void shouldClearEpicAfterDeletingAllSubtasks() {
        Epic epic = new Epic("epic title", "epic description");
        int epicId = taskManager.createEpic(epic);
        Subtask sub1 = new Subtask("sub1", "desc1", epicId);
        Subtask sub2 = new Subtask("sub2", "desc2", epicId);
        taskManager.createSubtask(sub1);
        taskManager.createSubtask(sub2);
        taskManager.deleteAllSubtasks();
        List<Subtask> remainingSubtasks = taskManager.getAllEpicSubtasks(epicId);
        assertTrue(remainingSubtasks.isEmpty(), "После удаления всех подзадач эпик должен быть пустым");
        assertEquals(Status.NEW, taskManager.getEpic(epicId).getStatus(), "Статус эпика должен быть NEW после удаления всех подзадач");
    }

    @Test
    void shouldNotKeepDeletedEpicInManager() {
        Epic epic = new Epic("epic title", "epic description");
        taskManager.createEpic(epic);
        taskManager.getEpic(epic.getId());
        taskManager.deleteEpic(epic.getId());
        assertNull(taskManager.getEpic(epic.getId()), "Удаленный эпик не должен возвращаться из менеджера");
    }

    @Test
    void changingTaskFieldsShouldReflectInManager() {
        Task task = new Task("title", "description");
        int id = taskManager.createTask(task);
        task.setTitle("new title");
        task.setDescription("new description");
        task.setStatus(Status.DONE);
        Task storedTask = taskManager.getTask(id);
        assertEquals("new title", storedTask.getTitle(), "Название задачи должно обновиться в менеджере");
        assertEquals("new description", storedTask.getDescription(), "Описание задачи должно обновиться в менеджере");
        assertEquals(Status.DONE, storedTask.getStatus(), "Статус задачи должен обновиться в менеджере");
    }

    @Test
    void deleteAllTasksShouldClearManager() {
        Task task1 = new Task("task1", "desc1");
        Task task2 = new Task("task2", "desc2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.deleteAllTasks();
        assertTrue(taskManager.getAllTasks().isEmpty(), "Все задачи должны быть удалены");
    }

    @Test
    void deleteAllEpicsShouldClearManager() {
        Epic epic1 = new Epic("epic1", "desc1");
        Epic epic2 = new Epic("epic2", "desc2");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.deleteAllEpics();
        assertTrue(taskManager.getAllEpics().isEmpty(), "Все эпики должны быть удалены");
        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Все подзадачи должны быть удалены");
    }

    @Test
    void deletingEpicRemovesSubtasksFromManager() {
        Epic epic = new Epic("epic title", "epic description");
        int epicId = taskManager.createEpic(epic);
        taskManager.getEpic(epicId);
        Subtask subtask = new Subtask("sub", "desc", epicId);
        int subId = taskManager.createSubtask(subtask);
        taskManager.getSubtask(subId);
        taskManager.deleteEpic(epicId);
        assertNull(taskManager.getSubtask(subId), "Подзадачи удаленного эпика не должны оставаться в менеджере");
    }
}

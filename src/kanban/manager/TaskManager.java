package kanban.manager;

import kanban.model.Epic;
import kanban.model.Subtask;
import kanban.model.Task;

import java.util.List;

public interface TaskManager {
    // просмотр истории
    List<Task> getHistory();

    // задачи
    int createTask(Task task);

    void updateTask(Task task);

    Task getTask(int id);

    List<Task> getAllTasks();

    void deleteTask(int id);

    void deleteAllTasks();

    // эпики
    int createEpic(Epic epic);

    void updateEpic(Epic epic);

    Epic getEpic(int id);

    List<Epic> getAllEpics();

    List<Subtask> getAllEpicSubtasks(int epicId);

    void deleteEpic(int id);

    void deleteAllEpics();

    // подзадачи
    int createSubtask(Subtask subtask);

    void updateSubtask(Subtask subtask);

    Subtask getSubtask(int id);

    List<Subtask> getAllSubtasks();

    void deleteSubtask(int id);

    void deleteAllSubtasks();
}

package ru.practicum.manager;

import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.ArrayList;

public interface TaskManager {
    // генерация идентификаторов
    int createId();

    // просмотр истории
    ArrayList<Task> getHistory();

    // задачи
    int createTask(Task task);

    void updateTask(Task task);

    Task getTask(int id);

    ArrayList<Task> getAllTasks();

    void deleteTask(int id);

    void deleteAllTasks();

    // эпики
    int createEpic(Epic epic);

    void updateEpic(Epic epic);

    void updateEpicStatus(Epic epic);

    Epic getEpic(int id);

    ArrayList<Epic> getAllEpics();

    ArrayList<Subtask> getAllEpicSubtasks(int epicId);

    void deleteEpic(int id);

    void deleteAllEpics();

    // подзадачи
    int createSubtask(Subtask subtask);

    void updateSubtask(Subtask subtask);

    Subtask getSubtask(int id);

    ArrayList<Subtask> getAllSubtasks();

    void deleteSubtask(int id);

    void deleteAllSubtasks();
}

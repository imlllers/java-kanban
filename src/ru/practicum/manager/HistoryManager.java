package ru.practicum.manager;

import ru.practicum.model.Task;

import java.util.List;

public interface HistoryManager {
    // добавление просмотренных задач
    void add(Task task);

    // список просмотренных задач
    List<Task> getHistory();
}

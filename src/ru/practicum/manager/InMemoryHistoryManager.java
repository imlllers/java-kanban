package ru.practicum.manager;

import ru.practicum.model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    ArrayList<Task> history;

    public InMemoryHistoryManager() {
        history = new ArrayList<>();
    }

    // добавление просмотренных задач
    @Override
    public void add(Task task) {
        Task viewedTask = new Task(task.getTitle(), task.getDescription());
        viewedTask.setId(task.getId());
        viewedTask.setStatus(task.getStatus());
        if (history.size() == 10) {
            history.removeFirst();
            history.add(viewedTask);
        } else {
            history.add(viewedTask);
        }
    }

    // список просмотренных задач
    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }
}

package ru.practicum.manager;

import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> history;

    public InMemoryHistoryManager() {
        history = new ArrayList<>();
    }

    // добавление просмотренных задач
    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        Task viewedTask = createTaskCopy(task);

        if (history.size() == 10) {
            history.removeFirst();
        }
        history.add(viewedTask);
    }

    private Task createTaskCopy(Task task) {
        if (task instanceof Epic) {
            Epic epic = (Epic) task;
            Epic copy = new Epic(epic.getTitle(), epic.getDescription());
            copy.setId(epic.getId());
            copy.setStatus(epic.getStatus());

            for (Integer subId : epic.getSubtaskIds()) {
                copy.addSubtask(subId);
            }
            return copy;
        } else if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            Subtask copy = new Subtask(subtask.getTitle(), subtask.getDescription(), subtask.getEpicId());
            copy.setId(subtask.getId());
            copy.setStatus(subtask.getStatus());
            return copy;
        } else {
            Task copy = new Task(task.getTitle(), task.getDescription());
            copy.setId(task.getId());
            copy.setStatus(task.getStatus());
            return copy;
        }
    }

    // список просмотренных задач
    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(history);
    }
}

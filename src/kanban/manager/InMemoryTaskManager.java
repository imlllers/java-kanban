package kanban.manager;

import kanban.model.Epic;
import kanban.model.Status;
import kanban.model.Subtask;
import kanban.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    private int counter;

    // генерация идентификаторов
    private int createId() {
        return ++counter;
    }

    // просмотр истории
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    // задачи
    @Override
    public int createTask(Task task) {
        int id = createId();
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    // эпики
    @Override
    public int createEpic(Epic epic) {
        int id = createId();
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic storedEpic = epics.get(epic.getId());
            storedEpic.setTitle(epic.getTitle());
            storedEpic.setDescription(epic.getDescription());
            updateEpicStatus(storedEpic);
        }
    }

    private void updateEpicStatus(Epic epic) {
        List<Integer> subIds = epic.getSubtaskIds();
        if (subIds.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        boolean allNew = true;
        boolean allDone = true;
        for (Integer subId : subIds) {
            Subtask sub = subtasks.get(subId);
            if (sub != null) {
                if (sub.getStatus() != Status.DONE) allDone = false;
                if (sub.getStatus() != Status.NEW) allNew = false;
            }
        }
        if (allDone) {
            epic.setStatus(Status.DONE);
        } else if (allNew) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllEpicSubtasks(int epicId) {
        Epic epic = epics.get(epicId);
        List<Subtask> allSubtasks = new ArrayList<>();
        if (epic != null) {
            for (Integer subId : epic.getSubtaskIds()) {
                Subtask subtask = subtasks.get(subId);
                if (subtask != null) {
                    allSubtasks.add(subtask);
                }
            }
        }
        return allSubtasks;
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Integer subId : epic.getSubtaskIds()) {
                subtasks.remove(subId);
                historyManager.remove(id);
            }
        }
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    // подзадачи
    @Override
    public int createSubtask(Subtask subtask) {
        if (!epics.containsKey(subtask.getEpicId())) {
            return -1;
        }
        if (subtask.getId() == subtask.getEpicId()) {
            return -1;
        }
        int id = createId();
        subtask.setId(id);
        subtasks.put(id, subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubtask(id);
        updateEpicStatus(epic);
        return id;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                updateEpicStatus(epic);
            }
        }
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(id);
                historyManager.remove(id);
                updateEpicStatus(epic);
            }
        }
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
            updateEpicStatus(epic);
        }
    }
}

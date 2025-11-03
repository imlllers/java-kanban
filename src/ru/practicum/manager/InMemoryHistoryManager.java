package ru.practicum.manager;

import ru.practicum.model.Node;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node<Task>> idToNode = new HashMap<>();
    private Node<Task> first;
    private Node<Task> last;

    private void linkLast(Task task) {
        Node<Task> oldLast = last;
        Node<Task> newLast = new Node<>(task, null, oldLast);
        last = newLast;
        if (oldLast == null) {
            first = newLast;
        } else {
            oldLast.next = newLast;
        }
    }

    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node<Task> current = first;
        while (current != null) {
            tasks.add(current.data);
            current = current.next;
        }
        return tasks;
    }

    private void removeNode(Node<Task> node) {
        if (node == null) return;
        Node<Task> prev = node.prev;
        Node<Task> next = node.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
        node.data = null;
        node.prev = null;
        node.next = null;
    }

    @Override
    public void remove(int id) {
        Node<Task> node = idToNode.remove(id);
        if (node != null) {
            removeNode(node);
        }
    }

    @Override
    public void add(Task task) {
        if (task == null) return;
        if (idToNode.containsKey(task.getId())) {
            removeNode(idToNode.get(task.getId()));
        }
        linkLast(task);
        idToNode.put(task.getId(), last);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
}

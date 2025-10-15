package kanban.manager;

import kanban.model.Node;
import kanban.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<Integer, Node<Task>> idToNode;
    private Node<Task> first;
    private Node<Task> last;

    public InMemoryHistoryManager() {
        idToNode = new HashMap<>();
    }

    public void linkLast(Task task) {
        Node<Task> oldLast = last;
        Node<Task> newLast = new Node<>(task, null, oldLast);
        last = newLast;
        if (oldLast == null) {
            first = last;
        } else {
            oldLast.next = newLast;
        }
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node<Task> currentTask = first;
        while (currentTask != null) {
            tasks.add(currentTask.data);
            currentTask = currentTask.next;
        }
        return tasks;
    }

    public void removeNode(Node<Task> node) {
        Node<Task> prev = node.prev;
        Node<Task> next = node.next;

        if (prev == null) {
            first = next;
            if (next != null) {
                next.prev = null;
            }
        } else {
            prev.next = null;
        }
        if (next == null) {
            last = prev;
            if (prev != null) {
                prev.next = null;
            }
        } else {
            next.prev = null;
        }
        node.data = null;
        node.prev = null;
        node.next = null;
    }

    @Override
    public void remove(int id) {
        removeNode(idToNode.remove(id));
    }

    @Override
    public void add(Task task) {
        if (idToNode.containsKey(task.getId())) {
            Node<Task> node = idToNode.get(task.getId());
            removeNode(node);
        }
        linkLast(task);
        idToNode.put(task.getId(), last);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
}

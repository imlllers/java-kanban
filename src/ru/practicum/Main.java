package ru.practicum;

import ru.practicum.manager.TaskManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        Task task1 = new Task("Почитать книгу", "Прочесть 3 главы Мастер и Маргарита");
        Task task2 = new Task("Оформить карту", "Съездить в банк");

        manager.createTask(task1);
        manager.createTask(task2);

        Epic epic1 = new Epic("Английский", "Позаниматься английским");
        manager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Учить", "Пройти тему 4", epic1.getId());
        Subtask subtask2 = new Subtask("Повторять", "Повторить 2 и 3 темы", epic1.getId());

        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);

        Epic epic2 = new Epic("Подготовка к презентации", "Подготовить презентацию для бизнес-встречи");
        manager.createEpic(epic2);

        Subtask subtask3 = new Subtask("Подготовить слайды", "Изучить тему и подготовить слайды", epic2.getId());
        manager.createSubtask(subtask3);

        printAll(manager);

        task1.setStatus(Status.DONE);
        manager.updateTask(task1);

        subtask1.setStatus(Status.DONE);
        manager.updateSubtask(subtask1);

        subtask2.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(subtask2);

        subtask3.setStatus(Status.DONE);
        manager.updateSubtask(subtask3);

        System.out.println("\n~~~ ПОСЛЕ ИЗМЕНЕНИЯ СТАТУСОВ ~~~");
        printAll(manager);

        manager.deleteTask(task2.getId());
        manager.deleteEpic(epic1.getId());

        System.out.println("\n~~~ ПОСЛЕ УДАЛЕНИЯ ~~~");
        printAll(manager);
    }

    private static void printAll(TaskManager manager) {
        System.out.println("\nЗАДАЧИ");
        System.out.println(manager.getAllTasks());

        System.out.println("\nЭПИКИ");
        System.out.println(manager.getAllEpics());

        System.out.println("\nПОДЗАДАЧИ");
        System.out.println(manager.getAllSubtasks());
    }
}

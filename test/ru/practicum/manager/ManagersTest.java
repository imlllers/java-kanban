package ru.practicum.manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagersTest {
    // утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров
    @Test
    void shouldReturnInitializedManagers() {
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(taskManager, "TaskManager должен быть проинициализирован");
        assertNotNull(historyManager, "HistoryManager должен быть проинициализирован");
    }
}
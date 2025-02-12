package be.vinci.ipl.cae.cae_exercices_exemple.services;

import be.vinci.ipl.cae.cae_exercices_exemple.models.Task;
import be.vinci.ipl.cae.cae_exercices_exemple.repositories.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    public Iterable<Task> findByCompleted(boolean completed) {
        return taskRepository.findByCompleted(completed);
    }

    public Task findOne(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task create(String title) {
        Task task = new Task();
        task.setId(null);
        task.setTitle(title);
        task.setCompleted(false);
        return taskRepository.save(task);
    }

    public boolean validTitle(String title) {
        return title != null && !title.isBlank();
    }

    public boolean conflictExists(String title) {
        return taskRepository.existsByTitle(title);
    }

    public boolean doesNotExist(Long id) {
        return !taskRepository.existsById(id);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public boolean isCompleted(Long id) {
        Task task = findOne(id);
        return task != null && task.isCompleted();
    }

    public void markCompleted(Long id) {
        Task task = findOne(id);
        if (task != null) {
            task.setCompleted(true);
            taskRepository.save(task);
        }
    }

}

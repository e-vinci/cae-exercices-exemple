package be.vinci.ipl.cae.cae_exercices_exemple.controllers;

import be.vinci.ipl.cae.cae_exercices_exemple.models.NewTask;
import be.vinci.ipl.cae.cae_exercices_exemple.models.Task;
import be.vinci.ipl.cae.cae_exercices_exemple.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping({"", "/"})
    public Iterable<Task> findAll(@RequestParam(required = false) Boolean completed) {
        if (completed != null) return taskService.findByCompleted(completed);
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Task findOne(@PathVariable Long id) {
        Task task = taskService.findOne(id);
        if (task == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return task;
    }

    @PostMapping({"", "/"})
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@RequestBody NewTask newTask) {
        String title = newTask.getTitle();
        if (!taskService.validTitle(title)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (taskService.conflictExists(title)) throw new ResponseStatusException(HttpStatus.CONFLICT);
        return taskService.create(title);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (taskService.doesNotExist(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        taskService.delete(id);
    }

    @PatchMapping("/{id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markCompleted(@PathVariable Long id) {
        if (taskService.doesNotExist(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (taskService.isCompleted(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        taskService.markCompleted(id);
    }

}

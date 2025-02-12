package be.vinci.ipl.cae.cae_exercices_exemple.repositories;

import be.vinci.ipl.cae.cae_exercices_exemple.models.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    Iterable<Task> findByCompleted(boolean completed);
    boolean existsByTitle(String title);
}

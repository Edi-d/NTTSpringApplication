package org.nttspringapp.repository;

import org.nttspringapp.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
//    @Query("SELECT t FROM Task t WHERE t.owner.id = :ownerId")
    List<Task> findByOwnerId(Long ownerId);
}

package org.example.repository;

import org.example.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByOwnerId(@Param("owner_Id") Long ownerId);
}
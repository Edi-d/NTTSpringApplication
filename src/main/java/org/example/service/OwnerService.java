package org.example.service;

import lombok.AllArgsConstructor;

import org.example.domain.entity.Owner;
import org.example.domain.entity.Task;
import org.example.repository.OwnerRepository;
import org.example.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public Optional<Owner> getOwnerById(Long ownerId) {
        return ownerRepository.findById(ownerId);
    }

    public Optional<List<Task>> listTasksByOwner(Long ownerId) {
        return ownerRepository.findById(ownerId).map(Owner::getTasks);
    }

    public Optional<Owner> getOwnerByEmail(String email) {
        return ownerRepository.findByEmail(email);
    }
}

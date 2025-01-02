package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.domain.entity.Owner;
import org.example.domain.entity.Task;
import org.example.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/owner")
public class OwnerController {
    private final OwnerService ownerService;

    @GetMapping("/{ownerId}")
    @PreAuthorize("hasRole('USER')")
    public Optional<Owner> getOwnerById(@PathVariable Long ownerId) {
        return ownerService.getOwnerById(ownerId);
    }

    @GetMapping("/{ownerId}/tasks")
    @PreAuthorize("hasRole('USER')")
    public Optional<List<Task>> listTasksByOwner(@PathVariable Long ownerId) {
        return ownerService.listTasksByOwner(ownerId);
    }
}

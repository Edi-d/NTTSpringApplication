package org.nttspringapp.repository;

import org.nttspringapp.domain.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}


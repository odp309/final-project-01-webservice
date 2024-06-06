package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BranchRepository extends JpaRepository<Branch, UUID> {

    Branch findByName(String name);
}

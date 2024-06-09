package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BranchRepository extends JpaRepository<Branch, UUID> {

    Branch findByName(String name);

    @Query("""
            select
            	b
            from
            	Branch b
            order by
            	(6371 * acos(cos(radians(:latitude)) * cos(radians(b.latitude)) * cos(radians(b.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(b.latitude))))
            """)
    List<Branch> findAllBranchOrderByDistance(@Param("latitude") double latitude, @Param("longitude") double longitude);
}

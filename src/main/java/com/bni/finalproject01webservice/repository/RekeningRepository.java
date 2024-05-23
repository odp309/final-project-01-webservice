package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.Rekening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RekeningRepository extends JpaRepository<Rekening, String> {
    Rekening findByNoRekening(String noRekening);
}

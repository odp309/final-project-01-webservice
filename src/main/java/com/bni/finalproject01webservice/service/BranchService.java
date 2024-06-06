package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.BranchInterface;
import com.bni.finalproject01webservice.model.Branch;
import com.bni.finalproject01webservice.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BranchService implements BranchInterface {

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public InitResponseDTO initBranch() {
        Branch currGambir = branchRepository.findByName("GAMBIR");
        Branch currGrahaPangeranSurabaya = branchRepository.findByName("GRAHA PANGERAN SURABAYA");
        Branch currMalang = branchRepository.findByName("MALANG");

        Branch gambir = new Branch();
        Branch grahaPangeranSurabaya = new Branch();
        Branch malang = new Branch();

        if (currGambir == null) {
            gambir.setName("GAMBIR");
            gambir.setCodeArea(15);
            gambir.setCity("JAKARTA PUSAT");
            gambir.setProvince("DKI JAKARTA");
            gambir.setAddress("JL. KEBON SIRIH NO. 51-53, JAK-PUS");
            gambir.setPhone("081344829471");
            gambir.setType("KANTOR CABANG UTAMA");
            gambir.setCreatedAt(new Date());
            branchRepository.save(gambir);
        }

        if (currGrahaPangeranSurabaya == null) {
            grahaPangeranSurabaya.setName("GRAHA PANGERAN SURABAYA");
            grahaPangeranSurabaya.setCodeArea(6);
            grahaPangeranSurabaya.setCity("SURABAYA");
            grahaPangeranSurabaya.setProvince("JAWA TIMUR");
            grahaPangeranSurabaya.setAddress("JL.ACHMAD YANI NO.286, SURABAYA, GEDUNG GRAHA PANGERAN LT.1-2");
            grahaPangeranSurabaya.setPhone("081329577231");
            grahaPangeranSurabaya.setType("KANTOR CABANG UTAMA");
            grahaPangeranSurabaya.setCreatedAt(new Date());
            branchRepository.save(grahaPangeranSurabaya);
        }

        if (currMalang == null) {
            malang.setName("MALANG");
            malang.setCodeArea(18);
            malang.setCity("MALANG");
            malang.setProvince("JAWA TIMUR");
            malang.setAddress("JL. JEND. BASUKI RAHMAT NO.75-77, MALANG");
            malang.setPhone("081354539000");
            malang.setType("KANTOR CABANG UTAMA");
            malang.setCreatedAt(new Date());
            branchRepository.save(malang);
        }

        InitResponseDTO response = new InitResponseDTO();
        response.setMessage("Branch init done!");

        return response;
    }
}

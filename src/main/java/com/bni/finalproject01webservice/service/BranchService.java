package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.branch.request.BranchRequestDTO;
import com.bni.finalproject01webservice.dto.branch.response.BranchResponseDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.request.AddBranchReserveRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.response.BranchReserveResponseDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.BranchInterface;
import com.bni.finalproject01webservice.model.Branch;
import com.bni.finalproject01webservice.model.BranchReserve;
import com.bni.finalproject01webservice.repository.BranchRepository;
import com.bni.finalproject01webservice.repository.BranchReserveRepository;
import com.bni.finalproject01webservice.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchService implements BranchInterface {

    private final BranchRepository branchRepository;
    private final BranchReserveRepository branchReserveRepository;
    private final CurrencyRepository currencyRepository;

    private final BranchReserveService branchReserveService;

    @Override
    public InitResponseDTO initBranch() {
        Optional<Branch> currGambir = branchRepository.findById("9007");
        Optional<Branch> currGrahaPangeranSurabaya = branchRepository.findById("9121");
        Optional<Branch> currMalang = branchRepository.findById("9070");

        List<String> currencyCodes = currencyRepository.findDistinctCurrencyCodes();

        Branch gambir = new Branch();
        Branch grahaPangeranSurabaya = new Branch();
        Branch malang = new Branch();

        if (currGambir.isEmpty()) {
            gambir.setCode("9007");
            gambir.setName("GAMBIR");
            gambir.setCity("JAKARTA PUSAT");
            gambir.setProvince("DKI JAKARTA");
            gambir.setAddress("JL. KEBON SIRIH NO. 51-53, JAK-PUS");
            gambir.setPhone("081344829471");
            gambir.setType("KANTOR CABANG UTAMA/KCU");
            gambir.setLatitude(-6.182840832410299);
            gambir.setLongitude(106.82785241195475);
            gambir.setCreatedAt(new Date());
            branchRepository.save(gambir);

            for (String code : currencyCodes) {
                BranchReserve reserve = branchReserveRepository.findByBranchCodeAndCurrencyCode(gambir.getCode(), code);
                if (reserve == null) {
                    AddBranchReserveRequestDTO request = new AddBranchReserveRequestDTO();
                    request.setBranchCode(gambir.getCode());
                    request.setCurrencyCode(code);
                    branchReserveService.addBranchReserve(request);
                }
            }
        }

        if (currGrahaPangeranSurabaya.isEmpty()) {
            grahaPangeranSurabaya.setCode("9121");
            grahaPangeranSurabaya.setName("GRAHA PANGERAN SURABAYA");
            grahaPangeranSurabaya.setCity("SURABAYA");
            grahaPangeranSurabaya.setProvince("JAWA TIMUR");
            grahaPangeranSurabaya.setAddress("JL.ACHMAD YANI NO.286, SURABAYA, GEDUNG GRAHA PANGERAN LT.1-2");
            grahaPangeranSurabaya.setPhone("081329577231");
            grahaPangeranSurabaya.setType("KANTOR CABANG UTAMA/KCU");
            grahaPangeranSurabaya.setLatitude(-7.344216376359213);
            grahaPangeranSurabaya.setLongitude(112.7285092273135);
            grahaPangeranSurabaya.setCreatedAt(new Date());
            branchRepository.save(grahaPangeranSurabaya);

            for (String code : currencyCodes) {
                BranchReserve reserve = branchReserveRepository.findByBranchCodeAndCurrencyCode(grahaPangeranSurabaya.getCode(), code);
                if (reserve == null) {
                    AddBranchReserveRequestDTO request = new AddBranchReserveRequestDTO();
                    request.setBranchCode(grahaPangeranSurabaya.getCode());
                    request.setCurrencyCode(code);
                    branchReserveService.addBranchReserve(request);
                }
            }
        }

        if (currMalang.isEmpty()) {
            malang.setCode("9070");
            malang.setName("MALANG");
            malang.setCity("MALANG");
            malang.setProvince("JAWA TIMUR");
            malang.setAddress("JL. JEND. BASUKI RAHMAT NO.75-77, MALANG");
            malang.setPhone("081354539000");
            malang.setType("KANTOR CABANG UTAMA/KCU");
            malang.setLatitude(-7.975701057723503);
            malang.setLongitude(112.62908908198607);
            malang.setCreatedAt(new Date());
            branchRepository.save(malang);

            for (String code : currencyCodes) {
                BranchReserve reserve = branchReserveRepository.findByBranchCodeAndCurrencyCode(malang.getCode(), code);
                if (reserve == null) {
                    AddBranchReserveRequestDTO request = new AddBranchReserveRequestDTO();
                    request.setBranchCode(malang.getCode());
                    request.setCurrencyCode(code);
                    branchReserveService.addBranchReserve(request);
                }
            }
        }

        InitResponseDTO response = new InitResponseDTO();
        response.setMessage("Branch init done!");

        return response;
    }

    @Override
    public List<BranchResponseDTO> getAllBranchOrderByDistance(BranchRequestDTO request) {
        List<Branch> branches = branchRepository.findAllBranchOrderByDistance(request.getLatitude(), request.getLongitude(), request.getAmountToWithdraw(), request.getCurrencyCode());

        return branches.stream()
                .map(branch -> {
                    BranchResponseDTO response = new BranchResponseDTO();
                    response.setCode(branch.getCode());
                    response.setName(branch.getName());
                    response.setType(branch.getType());
                    response.setPhone(branch.getPhone());
                    response.setAddress(branch.getAddress());
                    response.setCity(branch.getCity());
                    response.setProvince(branch.getProvince());
                    return response;
                })
                .collect(Collectors.toList());
    }
}
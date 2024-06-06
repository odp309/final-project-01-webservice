package com.bni.finalproject01webservice.dto.employee.response;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class DataEmployeeResponseDTO {

    private List<GetAllEmployee> Employee;

    @Getter
    @Setter
    public static class GetAllEmployee {
        private UUID id;
        private Date createdAt;
        private String email;
        private String firstName;
        private Boolean isActive;
        private String lastName;
        private String nip;
        private Date updatedAt;
        private UUID branchId;
        private Long roleId;
    }


}

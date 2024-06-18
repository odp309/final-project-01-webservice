package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.model.Employee;
import com.bni.finalproject01webservice.model.PasswordResetToken;

public interface PasswordResetTokenInterface {

    PasswordResetToken createPasswordResetTokenEmployee(Employee employee);

    Boolean isPasswordResetTokenExpired(PasswordResetToken passwordResetToken);
}

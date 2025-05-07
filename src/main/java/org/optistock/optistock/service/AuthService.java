package org.optistock.optistock.service;

import org.optistock.optistock.dto.LoginRequest;
import org.optistock.optistock.dto.RegistroRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> registrar(RegistroRequest request);
    ResponseEntity<?> login(LoginRequest request);
}

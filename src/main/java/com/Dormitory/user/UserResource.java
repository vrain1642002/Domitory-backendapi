package com.Dormitory.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Dormitory.authentication.AuthResponseDTO;
import com.Dormitory.message.SuccessMessage;
import com.Dormitory.role.Role;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401","https://dormiotry-frontend-student-production.up.railway.app","https://dormitory-frontend-admin-production.up.railway.app"})
@RequestMapping("api/v1/user")
public class UserResource {
    
    private UserService userService;

    @Autowired //tiêm phụ thuộc vào
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<SuccessMessage> register( @Valid @RequestBody User user) {
        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessMessage("Account successfully created",HttpStatusCode.valueOf(201).value()));
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody User user){ 
        String token = userService.login(user);
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDTO(token));
    }

    @GetMapping("role/{username}")
    public ResponseEntity<List<String>> getRoleByUsername(@PathVariable("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getRoleByUsername(username));
    }
    @PutMapping("change-password/{username}")
    public ResponseEntity<SuccessMessage> changePassword(
            @PathVariable("username") String username,
            @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(username, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

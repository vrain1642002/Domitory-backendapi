package com.Dormitory.student;

import java.util.Optional;

import com.Dormitory.message.SuccessMessage;
import com.Dormitory.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/student")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401","https://dormitory-management-frontend-production.up.railway.app/","https://dormitory-admin-frontend-production.up.railway.app"})
public class StudentResource {
    
    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<StudentResponseDTO> getStudentByNoStudent(@RequestParam("numberStudent") String numberStudent) {
        Optional<Student> student = studentService.getStudentByNoStudent(numberStudent);

        if(student.isPresent()) {
            StudentResponseDTO studentResponseDTO = new StudentResponseDTO(
            student.get().getId(),
            student.get().getNumberStudent(),
            student.get().getName(),
            student.get().getAddress(),
            student.get().getEmail(),
            student.get().getPhone(),
            student.get().getMajor(),
            student.get().getBirthday(),
            student.get().getGender(),
            student.get().getClassroom(),
            student.get().getPrioritize(),
            student.get().getStatus()) ;
            return ResponseEntity.status(HttpStatus.OK).body(studentResponseDTO);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping()
    public ResponseEntity<Void> updateStatus(@RequestParam("numberStudent") String numberStudent,@RequestParam("status") Integer status) {
        studentService.updateStatus(numberStudent,status);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("gratuation")
    public ResponseEntity<Page<Student>> getStudentGratuation(@PageableDefault(size = 2) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentGratuation(pageable));
    }
    @PostMapping("register")
    public ResponseEntity<SuccessMessage> register(@Valid @RequestBody Student student) {
        studentService.register(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessMessage("Đăng kí thành cng", HttpStatusCode.valueOf(201).value()));
    }

}

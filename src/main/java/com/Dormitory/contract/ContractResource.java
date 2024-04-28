package com.Dormitory.contract;

import java.util.List;

import com.Dormitory.feedback.FeedbackRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Dormitory.student.Student;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401","https://dormiotry-frontend-student-production.up.railway.app","https://dormitory-frontend-admin-production.up.railway.app"})
@RequestMapping("api/v1/contract")
public class ContractResource {
    @Autowired
    private ContractService contractService;

    @GetMapping("student/{studentId}/sesmester/{sesmesterId}")
    public ResponseEntity<Contract> getContract(@PathVariable("studentId") Integer studentId,@PathVariable("sesmesterId") Integer sesmesterId ) {
        Contract contract = contractService.getContract(studentId,sesmesterId);
        return ResponseEntity.ok().body(contract);
    }

    @GetMapping
    public ResponseEntity<Page<ContractResponseDTO>> getContractFromFilter(
        @RequestParam(required = false) Integer sesmester,
        @RequestParam(required = false) String schoolYear,
        @RequestParam(required = false) String major,
        @RequestParam(required = false) String numberStudent,
        @RequestParam(required = false) Integer status,
        @RequestParam(required = false) Integer gender,
        @PageableDefault(size = 6, sort = {"roomType", "numberRoom"}) Pageable pageable
    ) {
        
        return 
        ResponseEntity.status(HttpStatus.OK).body(contractService.getContractFromFilter(sesmester,schoolYear,major,numberStudent,status,gender,pageable));
    }
    @GetMapping("search")
    public ResponseEntity<Page<ContractResponseDTO>> getContractFromSearchFilter(
        @RequestParam(required = false) String search,
        @PageableDefault(size = 6, sort = {"roomType", "numberRoom"}) Pageable pageable
    ) {
        return 
        ResponseEntity.status(HttpStatus.OK).body(contractService.getContractFromFilter(search,pageable));
    }

    @PostMapping()
    public ResponseEntity<Void> addContract(@Valid @RequestBody Contract contract) {
        contractService.addContract(contract);
        return ResponseEntity.ok().build();
    }
    @PutMapping("{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable("id") Integer id,@RequestBody ContractResponseDTO contract) {
        contractService.updateStatus(id,contract);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("student/roomtype/{roomtypeId}/room/{numberRoom}")
    public ResponseEntity<List<Student>> getAllStudentsFromContract(@PathVariable("roomtypeId") Integer roomTypeId, @PathVariable("numberRoom") Integer numberRoom) {
        List<Student> students = contractService.getAllStudentsFromContract(roomTypeId,numberRoom);
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }
    // @PatchMapping("/register-service/{id}")
    // public ResponseEntity<Void> registerServices(@PathVariable Integer id,@RequestBody List<Services> services) {
    //     contractService.registerServices(id,services);
    //     return ResponseEntity.noContent().build();
    // }
}

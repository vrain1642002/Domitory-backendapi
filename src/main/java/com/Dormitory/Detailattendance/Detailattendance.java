package com.Dormitory.Detailattendance;
import com.Dormitory.admin.Admin;
import com.Dormitory.attendancelist.AttendanceList;
import com.Dormitory.student.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Detailattendance {
    @Id // Đánh dấu đây là ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // trường tăng tự động
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_Attendancelist")
    @JsonBackReference
    private AttendanceList attendanceList;

    @NotBlank(message = "Vui lòng nhập ly do vang")
    private String reason = new String();


    @ManyToOne
    @JoinColumn(name = "ID_Student")
    private Student student;

}

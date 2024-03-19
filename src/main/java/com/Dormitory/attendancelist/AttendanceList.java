package com.Dormitory.attendancelist;

import com.Dormitory.admin.Admin;
import com.Dormitory.student.Student;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendancelist {
    @Id // Đánh dấu đây là ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // trường tăng tự động
    private Integer id;
    @NotNull(message = "student_id cannot be null")
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @NotBlank(message = "Vui lòng nhập lí do văng của sinh viên")
    private String reason = new String();
    private String note = new String();
    @ManyToOne(optional = true)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @JsonSerialize
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "al")//ánh xạ tên biến bên Image
    private List<Student> students ;

}

package com.Dormitory.attendancelist;

import com.Dormitory.admin.Admin;
import com.Dormitory.student.Student;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceList {
    @Id // Đánh dấu đây là ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // trường tăng tự động
    private Integer id;

    @NotBlank(message = "Vui lòng nhập ly do vang")
    private String reason = new String();
    private String note;
    private LocalDate createDate = LocalDate.now();
    @JoinColumn(name = "admin_id") // Thay đổi tên cột trong cơ sở dữ liệu nếu cần
    @ManyToOne(optional = true) // optional được đặt thành true để cho phép null
    private Admin admin; // Admin duyệt phản hồi này, có thể null

    @JsonSerialize
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attendanceList")//ánh xạ tên biến bên Image
    private List<Student> students ;



}

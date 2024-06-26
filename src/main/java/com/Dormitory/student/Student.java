package com.Dormitory.student;

import java.time.LocalDate;

import com.Dormitory.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Tạo bảng trong CSDL
public class Student {
    
    @Id // Đánh dấu đây là ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // trường tăng tự động
    private Integer id;

    @Column(unique = true)
    private String numberStudent;

    private String name;
    
    private String address;
//    @Email(message = "Email invalid")
    private String email;
    private String phone;

    private String major;

    private LocalDate birthday;
    private Integer gender;//1 -> boy, 0-> girl
    private String classroom;

    //doi tuong uu tien
    private String prioritize;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private Integer status = 0;
    //0 -> ổn
    //1 -> ra trường rồi
    //2 -> danh sách đen
    //3 -> danh sách đen tự tạow


}

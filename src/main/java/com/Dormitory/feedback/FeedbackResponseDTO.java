package com.Dormitory.feedback;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponseDTO {
    private Integer id;
    private String request;
    private  String reponse;
    private LocalDate sendDate;
    private Integer status;
    private Integer type;

    private String noteFromAdmin;
}

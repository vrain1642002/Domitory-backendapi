package com.Dormitory.feedback;

import java.io.Console;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dormitory.admin.Admin;
import com.Dormitory.admin.AdminRepository;
import com.Dormitory.contract.Contract;
import com.Dormitory.contract.ContractRepository;
import com.Dormitory.email.Email;
import com.Dormitory.email.EmailService;
import com.Dormitory.exception.InvalidValueException;
import com.Dormitory.exception.NotFoundException;

import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.sesmester.SesmesterRepository;
import com.Dormitory.student.Student;
import com.Dormitory.student.StudentRepository;

@Service
public class FeedbackService {
    
    private FeedbackRepository FeedbackRepository;
    private StudentRepository studentRepository;
    private ContractRepository contractRepository;
    private SesmesterRepository sesmesterRepository;
    private EmailService emailService;
    private AdminRepository adminRepository;
    @Autowired
    public FeedbackService(EmailService emailService, FeedbackRepository roomFeedbackRepository, StudentRepository studentRepository,
                           ContractRepository contractRepository, SesmesterRepository sesmesterRepository, AdminRepository adminRepository) {
        this.FeedbackRepository = roomFeedbackRepository;
        this.studentRepository = studentRepository;
        this.contractRepository = contractRepository;
        this.sesmesterRepository = sesmesterRepository;
        this.emailService = emailService;
        this.adminRepository = adminRepository;
    }
    public List<Feedback> getAllFeedbacks() {
        return FeedbackRepository.findAll();
    }

    public void updateStatus(Integer id, FeedbackRequestDTO requestDTO) {
        if (requestDTO.getEditDate().compareTo(LocalDate.now()) <= 0) {
            throw new InvalidValueException("Ngày sửa chữa phải lớn hơn ngày hiện tại");
        }
        Feedback r = FeedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tồn tại phản hồi này"));
        Admin admin = adminRepository.findById(requestDTO.getAdminId()).orElseThrow(() -> new NotFoundException("Không tồn tại admin này"));

        r.setAdmin(admin);
        r.setStatus(requestDTO.getStatus());
       r.setReponse(requestDTO.getReponse());
        System.out.println(r.getStatus());

        System.out.println(requestDTO.getReponse());

        if (r.getType() == 1) {
            if (r.getStatus() == 1) {
                if (requestDTO.getEditDate().compareTo(LocalDate.now()) <= 0) {
                    throw new InvalidValueException("Ngày sửa chữa phải lớn hơn ngày hiện tại");
                }
                Email email = new Email(requestDTO.getStudent().getEmail(), "THÔNG BÁO NGÀY XUỐNG SỬA CHỮA VẬT CHẤT ", null);
                emailService.sendEmailForFeedback(email, requestDTO.getStudent(), requestDTO.getNumberRoom(), requestDTO.getEditDate());
            } else if (r.getStatus() == 2) {
                Email email = new Email(requestDTO.getStudent().getEmail(), "THÔNG BÁO ĐÃ SỬA CƠ SƠ VẬT CHẤT ", null);
                emailService.sendEmailForFeedback1(email, requestDTO.getStudent(), requestDTO.getNumberRoom(), requestDTO.getEditDate());
            }
        }

        if (r.getType() == 2) {
        if(r.getStatus()==1)
        {
            if (requestDTO.getEditDate().compareTo(LocalDate.now()) <= 0) {
                throw new InvalidValueException("Ngày đợi kết quả phản hồi phải lớn hơn ngày hiện tại");
            }
            Email email = new Email(requestDTO.getStudent().getEmail(), "THÔNG BÁO NGÀY ĐỢI KẾT QUẢ PHẨN HỒI ", null);
            emailService.sendEmailForFeedback3(email, requestDTO.getStudent(), requestDTO.getNumberRoom(), requestDTO.getEditDate());
        }
            else if (r.getStatus()==2){
            Email email = new Email(requestDTO.getStudent().getEmail(), "THÔNG BÁO KẾT QUẢ PHẢN HỒI ", null);
            emailService.sendEmailForFeedback4(email, requestDTO.getStudent(), requestDTO.getReponse());
            }
        }


            FeedbackRepository.save(r);

    }

    public List<FeedbackResponseDTO> getFeedbackByStudent(Integer id) {
        List<FeedbackResponseDTO> responseDTOs = new ArrayList<>();
        if(FeedbackRepository.findByStudentId(id).isPresent()) {
            List<Feedback> roomFeedbacks = FeedbackRepository.findByStudentId(id).get();
            for(Feedback r: roomFeedbacks) {
                FeedbackResponseDTO feedbackResponseDTO = new FeedbackResponseDTO();
                feedbackResponseDTO.setId(r.getId());
                feedbackResponseDTO.setStatus(r.getStatus());
                feedbackResponseDTO.setSendDate(r.getSendDate());
                feedbackResponseDTO.setRequest(r.getRequest());
                feedbackResponseDTO.setReponse(r.getReponse());
                feedbackResponseDTO.setType(r.getType());

                responseDTOs.add(feedbackResponseDTO);
            }
        }
        return responseDTOs;
    }
    public void addFeedBackFromStudent(Feedback roomFeedback) {
        Student student = studentRepository.findById(roomFeedback.getStudent().getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại sinh viên với id: "+roomFeedback.getStudent().getId()));
        Sesmester sesmester = sesmesterRepository.findSesmesterByCurrentDateBetweenStartDateAndEndDate(roomFeedback.getSendDate())
        .orElseThrow(() -> new NotFoundException("Học kỳ này chưa hoạt động"));
        Contract contract = contractRepository.findBySesmesterIdAndStudentId(sesmester.getId(), student.getId())
        .orElseThrow(() -> new NotFoundException("Sinh viên chưa đăng ký phòng ở học kỳ này"));
        if (contract.getStatus() == 0|| contract.getStatus()==2) {
            throw new NotFoundException("Sinh viên không phải là sinh viên đang lưu trú");
        }

        roomFeedback.setNumberRoom(contract.getNumberRoom());
        roomFeedback.setRequest(roomFeedback.getRequest());
        FeedbackRepository.save(roomFeedback);
    }
    
}

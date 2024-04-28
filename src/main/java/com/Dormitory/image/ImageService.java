package com.Dormitory.image;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


import com.Dormitory.student.Student;
import com.Dormitory.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Dormitory.exception.NotFoundException;
import com.Dormitory.roomtype.RoomType;
import com.Dormitory.roomtype.RoomTypeRepository;

@Service
public class ImageService {

    
    private ImageRepository repository;
    private RoomTypeRepository roomTypeRepository;
    private StudentRepository studentRepository;


    @Autowired
    public ImageService(ImageRepository repository, RoomTypeRepository roomTypeRepository,StudentRepository studentRepository) {
        this.repository = repository;
        this.roomTypeRepository = roomTypeRepository;
        this.studentRepository=studentRepository;
    }


    public String uploadImage(MultipartFile file, Integer roomId) throws IOException {

        RoomType roomType = new RoomType();

        if(roomTypeRepository.findById(roomId).isPresent()) {
            roomType = roomTypeRepository.findById(roomId).get();
        }else {
            throw new NotFoundException("Loại phòng không tìm thấy");
        }

        Image imageData = repository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .roomType(roomType)
                .build()
                );
        if (imageData != null) {
            return "File ảnh upload thành công : " + file.getOriginalFilename();
        }
        return null;
    }
    public String uploadImageSV(MultipartFile file, String numberStudent) throws IOException {

        Student student =new Student();
        if (studentRepository.findByNumberStudent(numberStudent).isPresent())
            student=studentRepository.findByNumberStudent(numberStudent).get();
        else {
            throw new NotFoundException("Không tìm thấy sinh viên");
        }
        Image imageData = repository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .student(student)
                .build()
        );
        if (imageData != null) {
            return "File ảnh upload thành công : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<Image> dbImageData = repository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

}
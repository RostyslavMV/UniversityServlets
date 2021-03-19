package com.rmv.university.service;

import com.rmv.university.entity.dao.Enrollment;
import com.rmv.university.entity.dao.User;
import com.rmv.university.entity.request.StudentEnrollmentRequest;
import com.rmv.university.repo.EnrollmentRepo;
import com.rmv.university.util.TokenUtil;

public class EnrollmentService {
    public static EnrollmentService INSTANCE = new EnrollmentService();

    private final EnrollmentRepo enrollmentRepo = EnrollmentRepo.INSTANCE;

    public void save(StudentEnrollmentRequest request){
        Enrollment enrollment = new Enrollment();
        User user = TokenUtil.getUserByToken(request.getToken());
        enrollment.setStudentId(user.getStudentId());
        enrollment.setCourseId(request.getCourseId());
        enrollmentRepo.save(enrollment);
    }
}

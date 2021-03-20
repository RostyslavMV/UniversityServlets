package com.rmv.university.service;

import com.rmv.university.entity.Course;
import com.rmv.university.entity.Enrollment;
import com.rmv.university.entity.User;
import com.rmv.university.entity.request.EnrollmentRequest;
import com.rmv.university.entity.request.StudentEnrollmentRequest;
import com.rmv.university.entity.response.LecturerCourseResponse;
import com.rmv.university.entity.response.LecturerCoursesResponse;
import com.rmv.university.entity.response.LecturerEnrollmentResponse;
import com.rmv.university.repo.CourseRepo;
import com.rmv.university.repo.EnrollmentRepo;
import com.rmv.university.repo.StudentRepo;
import com.rmv.university.util.TokenUtil;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {
  public static EnrollmentService INSTANCE = new EnrollmentService();

  private final EnrollmentRepo enrollmentRepo = EnrollmentRepo.INSTANCE;
  private final CourseRepo courseRepo = CourseRepo.INSTANCE;
  private final StudentRepo studentRepo = StudentRepo.INSTANCE;

  public void save(StudentEnrollmentRequest request) {
    Enrollment enrollment = new Enrollment();
    User user = TokenUtil.getUserByToken(request.getToken());
    enrollment.setStudentId(user.getStudentId());
    enrollment.setCourseId(request.getCourseId());
    enrollmentRepo.save(enrollment);
  }

  public LecturerCoursesResponse getLecturerResponse(String token) {
    LecturerCoursesResponse response = new LecturerCoursesResponse();
    List<LecturerCourseResponse> lecturerCourseResponses = new ArrayList<>();

    User user = TokenUtil.getUserByToken(token);
    List<Course> courses = courseRepo.findAllCoursesForLecturer(user.getLecturerId());

    for (Course course : courses) {
      LecturerCourseResponse lecturerCourseResponse = new LecturerCourseResponse();
      lecturerCourseResponse.setLecturerEnrollmentResponses(new ArrayList<>());
      lecturerCourseResponse.setCourseName(course.getName());
      lecturerCourseResponse.setCourseId(course.getId());
      List<Enrollment> enrollments = enrollmentRepo.getEnrollmentsForCourse(course.getId());
      for (Enrollment enrollment : enrollments) {
        LecturerEnrollmentResponse lecturerEnrollmentResponse = new LecturerEnrollmentResponse();
        lecturerEnrollmentResponse.setMark(enrollment.getMark());
        if (lecturerEnrollmentResponse.getMark() != null) {
          lecturerEnrollmentResponse.setIsReviewed(true);
        }
        lecturerEnrollmentResponse.setReview(enrollment.getReview());
        lecturerEnrollmentResponse.setStudentId(enrollment.getStudentId());
        lecturerEnrollmentResponse.setStudentName(
            studentRepo.getNameById(enrollment.getStudentId()));
        lecturerCourseResponse.getLecturerEnrollmentResponses().add(lecturerEnrollmentResponse);
      }
      lecturerCourseResponses.add(lecturerCourseResponse);
    }
    response.setCourseResponses(lecturerCourseResponses);
    return response;
  }

  public Enrollment updateEnrollment(EnrollmentRequest enrollmentRequest) {
    Enrollment enrollment = new Enrollment();
    enrollment.setStudentId(enrollmentRequest.getStudentId());
    enrollment.setCourseId(enrollmentRequest.getCourseId());
    enrollment.setMark(enrollmentRequest.getMark());
    enrollment.setReview(enrollmentRequest.getReview());
    return enrollmentRepo.changeMarkAndReview(enrollment);
  }
}

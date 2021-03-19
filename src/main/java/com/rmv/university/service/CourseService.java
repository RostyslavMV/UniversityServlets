package com.rmv.university.service;

import com.rmv.university.entity.dao.Course;
import com.rmv.university.entity.dao.Enrollment;
import com.rmv.university.entity.dao.User;
import com.rmv.university.entity.request.CourseRequest;
import com.rmv.university.entity.request.StudentCourseRequest;
import com.rmv.university.entity.response.CourseResponse;
import com.rmv.university.entity.response.StudentCourseListResponse;
import com.rmv.university.mappers.CourseMapper;
import com.rmv.university.repo.CourseRepo;
import com.rmv.university.repo.EnrollmentRepo;
import com.rmv.university.repo.UserRepo;
import com.rmv.university.util.TokenUtil;

import java.util.*;

public class CourseService {

  public static CourseService INSTANCE = new CourseService();

  private CourseRepo courseRepo = CourseRepo.INSTANCE;
  private UserRepo userRepo = UserRepo.INSTANCE;
  private EnrollmentRepo enrollmentRepo = EnrollmentRepo.INSTANCE;

  public StudentCourseListResponse getCourseListResponse(StudentCourseRequest request) {
    StudentCourseListResponse response = new StudentCourseListResponse();
    User user = TokenUtil.getUserByToken(request.getToken());
    response.setAvailableCourses(getAvailableCoursesResponsesForStudent(user.getStudentId()));
    response.setEnrolledCourses(getEnrolledCoursesResponsesForStudent(user.getStudentId()));
    return response;
  }

  public Course save(CourseRequest courseRequest) {
    Course course = getCourseFromRequest(courseRequest);
    return courseRepo.save(course);
  }

  private Course getCourseFromRequest(CourseRequest request) {
    Course course = new Course();
    User user = TokenUtil.getUserByToken(request.getToken());
    course.setName(request.getName());
    course.setLecturerId(user.getLecturerId());
    return course;
  }

  private List<CourseResponse> getAvailableCoursesResponsesForStudent(Integer studentId) {
    List<CourseResponse> courseResponses = new ArrayList<>();
    List<Course> courses = courseRepo.findAll();
    Set<Integer> allCoursesIds = new HashSet<>();
    for (Course course : courses) {
      allCoursesIds.add(course.getId());
    }

    List<Enrollment> enrollments = enrollmentRepo.findEnrollmentsByStudentId(studentId);
    Set<Integer> enrolledCoursesIds = new HashSet<>();
    for (Enrollment enrollment : enrollments) {
      enrolledCoursesIds.add(enrollment.getCourseId());
    }

    allCoursesIds.removeAll(enrolledCoursesIds);
    for (Course course : courses) {
      if (allCoursesIds.contains(course.getId())) {
        courseResponses.add(CourseMapper.INSTANCE.courseToCourseResponse(course));
      }
    }
    return courseResponses;
  }

  private List<CourseResponse> getEnrolledCoursesResponsesForStudent(Integer studentId) {
    List<CourseResponse> courseResponses = new ArrayList<>();
    List<Enrollment> enrollments = enrollmentRepo.findEnrollmentsByStudentId(studentId);
    for (Enrollment enrollment : enrollments) {
      Course course;
      Optional<Course> optionalCourse = courseRepo.findById(enrollment.getCourseId());
      if (optionalCourse.isPresent()) {
        course = optionalCourse.get();
      } else throw new RuntimeException("e");
      courseResponses.add(
          CourseMapper.INSTANCE.courseAndEnrollmentToCourseResponse(course, enrollment));
    }

    return courseResponses;
  }
}

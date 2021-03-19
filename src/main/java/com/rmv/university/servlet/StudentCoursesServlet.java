package com.rmv.university.servlet;

import com.rmv.university.entity.request.StudentCourseRequest;
import com.rmv.university.entity.response.StudentCourseListResponse;
import com.rmv.university.service.CourseService;
import com.rmv.university.util.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/student/course"})
public class StudentCoursesServlet extends HttpServlet {
  private final CourseService courseService = CourseService.INSTANCE;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String token = req.getParameter("token");
    StudentCourseRequest request = new StudentCourseRequest(token);

    StudentCourseListResponse response = courseService.getCourseListResponse(request);

    ResponseUtil.sendResponse(resp, response);
  }
}

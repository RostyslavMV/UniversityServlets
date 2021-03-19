package com.rmv.university.servlet;

import com.rmv.university.entity.dao.Course;
import com.rmv.university.entity.request.CourseRequest;
import com.rmv.university.service.CourseService;
import com.rmv.university.util.RequestUtil;
import com.rmv.university.util.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/lecturer/course"})
public class LecturerCoursesServlet extends HttpServlet {
  private CourseService courseService = CourseService.INSTANCE;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    CourseRequest request = RequestUtil.getRequestObject(req, CourseRequest.class);
    Course course = courseService.save(request);

    ResponseUtil.sendResponse(resp, course);
  }
}

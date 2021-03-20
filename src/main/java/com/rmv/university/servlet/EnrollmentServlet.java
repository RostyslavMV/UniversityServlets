package com.rmv.university.servlet;

import com.rmv.university.entity.Enrollment;
import com.rmv.university.entity.request.EnrollmentRequest;
import com.rmv.university.entity.request.StudentEnrollmentRequest;
import com.rmv.university.entity.response.LecturerCoursesResponse;
import com.rmv.university.service.EnrollmentService;
import com.rmv.university.util.RequestUtil;
import com.rmv.university.util.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/enrollment"})
public class EnrollmentServlet extends HttpServlet {
    private final EnrollmentService enrollmentService = EnrollmentService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        StudentEnrollmentRequest request = RequestUtil.getRequestObject(req, StudentEnrollmentRequest.class);

        enrollmentService.save(request);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getParameter("token");
        LecturerCoursesResponse response = enrollmentService.getLecturerResponse(token);
        ResponseUtil.sendResponse(resp, response);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        EnrollmentRequest request = RequestUtil.getRequestObject(req, EnrollmentRequest.class);
        Enrollment response = enrollmentService.updateEnrollment(request);
        ResponseUtil.sendResponse(resp, response);
    }
}

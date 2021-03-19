package com.rmv.university.servlet;

import com.rmv.university.entity.request.StudentEnrollmentRequest;
import com.rmv.university.service.EnrollmentService;
import com.rmv.university.util.RequestUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/enrollment"})
public class EnrollmentServlet extends HttpServlet {
    private final EnrollmentService enrollmentService = EnrollmentService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        StudentEnrollmentRequest request = RequestUtil.getRequestObject(req, StudentEnrollmentRequest.class);

        enrollmentService.save(request);
    }
}

package com.rmv.university.servlet;

import com.rmv.university.entity.dao.User;
import com.rmv.university.entity.request.UserCreateRequest;
import com.rmv.university.entity.response.UserResponse;
import com.rmv.university.mappers.UserMapper;
import com.rmv.university.service.UserService;
import com.rmv.university.util.RequestUtil;
import com.rmv.university.util.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/user"})
public class UserServlet extends HttpServlet {

  private final UserService userService = UserService.INSTANCE;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    UserCreateRequest request = RequestUtil.getRequestObject(req, UserCreateRequest.class);

    User user = userService.create(request);

    UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(user);
    ResponseUtil.sendResponse(resp, userResponse);
  }


}

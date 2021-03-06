package com.example.springbootdemo.web;

import com.example.springbootdemo.entity.UserInfo;
import com.example.springbootdemo.service.UserService;
import com.example.springbootdemo.common.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
@Api(value = "user", description = "用户接口")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value="测试接口")
    @GetMapping("/hello")
    public String say() {
        logger.info("Hello springboot");
        return "hello,this is a springbootdemo";
    }

    @ApiOperation(value="登录")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType="path")
    @PostMapping(value = "/login")
    public RestResponse<UserInfo> login(@RequestBody UserInfo userInfo, HttpServletResponse response,
                                        HttpServletRequest request, HttpSession httpSession){
        logger.info("[web-begin]登录：{}", userInfo);
        UserInfo user = userService.login(userInfo);
//        httpSession.setAttribute("userInfo", userInfo);
        Cookie cookie = new Cookie("realName", user.getRealName());
        cookie.setPath("/");
        response.addCookie(cookie);
        logger.info("[web-end]登陆成功。{}", user);
        return RestResponse.success(user);
    }

}

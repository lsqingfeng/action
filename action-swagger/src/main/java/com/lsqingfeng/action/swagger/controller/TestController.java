package com.lsqingfeng.action.swagger.controller;

import com.lsqingfeng.action.swagger.vo.test.CreateTestReqVO;
import com.lsqingfeng.action.swagger.vo.test.TestResVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @className: TestController
 * @description:
 *
 *
 * http://localhost:7878/doc.html
 * http://localhost:7878/swagger-ui.html
 * @author: sh.Liu
 * @date: 2021-01-19 16:11
 */
@RestController
@RequestMapping("/test")
@Api(value = "测试接口", tags = {"测试接口"})
public class TestController {

    @PostMapping
    public String test(@RequestBody CreateTestReqVO req) {
        return "success";
    }

    @GetMapping
    public TestResVO test2(@RequestBody CreateTestReqVO req) {
        return new TestResVO();
    }


}





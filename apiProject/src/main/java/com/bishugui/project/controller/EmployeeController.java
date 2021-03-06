package com.bishugui.project.controller;

import com.bishugui.project.pojo.Employees;
import com.bishugui.project.service.EmployeesService;
import com.bishugui.project.utils.JwtUtils;
import com.bishugui.project.utils.Result;
import com.bishugui.project.utils.ResultUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "/employee")
@RestController
public class EmployeeController {
    @Autowired
    EmployeesService employeeService;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("update-pwd")
    public Result updatePwd(@RequestBody Map<String,Object> map){
        String s = employeeService.updatePwd(map);
        return ResultUtils.success(s);
    }

    @GetMapping("/all")
    public Result getAll(){
        List<Employees> all = employeeService.getAll();
        return ResultUtils.success(all);
    }
    @GetMapping("/one")
    public Result getOne(@RequestParam("employeeId") Integer employeeId){
        Employees employee = employeeService.getEmployeeById(employeeId);
        System.out.println(employee.toString());
        return ResultUtils.success(employee);
    }

    @PostMapping("/insert-update")
    public Result insertOrUpdate(@RequestBody Employees employees){
        Employees employees1 = employeeService.insertOrUpdate(employees);
        return ResultUtils.success(employees);
    }
    @GetMapping("/user")
    public Result getUser(){
        Employees employees =employeeService.getEmployeeById(2100000);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("employees",employees);
        resultMap.put("imgPath","http://localhost:8082/images/");
        return ResultUtils.success(resultMap);
    }


    @PostMapping("/login")
    public Result login(@RequestBody Map<String,Object> map){
        Map<String, Object> login = employeeService.login(map);
        return ResultUtils.success(login);
//        Integer employeeId = Integer.parseInt(loginMap.get("employeeId"));
//        String pwd = loginMap.get("pwd");
//        Employees employees =employeeService.getEmployeeById(employeeId);
//        if(employees == null || !employees.getPwd().equals(pwd)){
//            return ResultUtils.fail("?????????????????????????????????");
//        }
//        Map<String,Object> resultMap = new HashMap<>();
//        resultMap.put("employee", employees);
//        String token = jwtUtils.createJwt(String.valueOf(employees.getEmployeeId()), employees.getPwd());
//        resultMap.put("token",token);
//        return ResultUtils.success(resultMap);
    }

    @PostMapping("/add")
    public Result add(HttpServletRequest request,
                      @RequestBody Map<String,String> map) throws Exception{
        /**
         * ???????????????????????????token??????
         *   1.??????????????????????????????=Authorization(???????????????)
         *   2.??????Bearer+??????
         *   3.??????token
         *   4.??????clamis
         */
        //1.??????????????????????????????=Authorization(???????????????)
         String authorization = request.getHeader("Authorization");
         if(authorization.isEmpty()){
             System.out.println("????????????");
             return ResultUtils.fail("????????????");
         }
         //2.??????Bearer+??????
         String token = authorization.replace("Bearer ", "");

         //3.??????token
         Claims claims = jwtUtils.parseJwt(token);
         //4.??????clamis
         String userId = claims.getId();
         map.put("userId",userId);
         return ResultUtils.success(map);
    }
}

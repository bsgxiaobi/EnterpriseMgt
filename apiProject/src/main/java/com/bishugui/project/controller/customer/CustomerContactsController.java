package com.bishugui.project.controller.customer;


import com.bishugui.project.pojo.customer.CustomerContacts;
import com.bishugui.project.service.customer.CustomerContactsService;
import com.bishugui.project.utils.Result;
import com.bishugui.project.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bishugui
 * @since 2021-05-10
 */
@RestController
@RequestMapping("/customer-contacts")
public class CustomerContactsController {
    @Autowired
    CustomerContactsService service;

    @GetMapping("all")
    public Result getAllCustomerContacts(@RequestParam("cid") Integer cid){
        System.out.println(cid);
        List<CustomerContacts> contactsList = service.getAllCustomerContacts(cid);
        return ResultUtils.success(contactsList);
    }

    @GetMapping("one")
    public Result getOneCustomerContacts(@RequestParam("ccId") Integer ccId){
        CustomerContacts contacts = service.getOneCustomerContacts(ccId);
        return ResultUtils.success(contacts);
    }

    @PostMapping("delete")
    public Result deleteOneContact(@RequestBody Map<String,Integer> map){
      Boolean bl = service.deleteOneContact(map.get("ccId"));
      if(bl) return ResultUtils.success("success");
      else return ResultUtils.success("error");
    }

    @PostMapping("insert-update")
    public Result insertOrUpdateContact(@RequestBody CustomerContacts contacts){
        CustomerContacts customerContacts = service.insertOrUpdateContact(contacts);
        return ResultUtils.success(customerContacts);
    }
}


package com.shi.address.controller;

import com.shi.address.pojo.Page;
import com.shi.address.pojo.People;
import com.shi.address.pojo.User;
import com.shi.address.service.PeopleService;
import com.shi.address.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PeopleService peopleService;

    static User user;
    static int uid;

    @RequestMapping("/login")
    public String login(String name, String password, int pageno, HttpSession session){
        String url = "error";
        user = userService.getUser(name);

        System.out.println(user);
        uid = user.getId();
        if(user!=null){
            if(user.getPassword().equals(password)){
                url = "userManagement";
                List sort = peopleService.getSort(uid);
                session.setAttribute("sort",sort);
                Page<People> currentPage = peopleService.getCurrentPage(pageno, uid);
                session.setAttribute("page",currentPage);
            }
        }
        return url;
    }

    @RequestMapping("/register")
    public String register(String name,String password,String slogan){
        HashMap map = new HashMap();
        map.put("name",name);
        map.put("password",password);
        map.put("slogan",slogan);
        userService.addUser(map);
        return "redirect:jsp/login.jsp";
    }

    @RequestMapping("/addPeople")
    public String addPeople(People people,HttpSession session){
        int userId = user.getId();
        people.setUid(userId);
        peopleService.addPeople(people);
        List sort = peopleService.getSort(uid);
        session.setAttribute("sort",sort);
        return "success";
    }

    @RequestMapping("/deletePeople")
    public String deletePeople(int id , HttpSession session){
        peopleService.deletePeople(id);
        List sort = peopleService.getSort(uid);
        session.setAttribute("sort",sort);
        return "userManagement";
    }

    @RequestMapping("/toRevise")
    public String revise(int id,HttpSession session){
        People peopleById = peopleService.getPeopleById(id);
        session.setAttribute("peopleId",peopleById);
        return "revisePeople";
    }

    @RequestMapping("/revisePeople")
    public String revisePeople(People people, HttpSession session){
        peopleService.revisePeople(people);
        return "userManagement";
    }

    @RequestMapping("/getSomePeople")
    public String getSomePeople(int pageno, People people, HttpSession session){
        System.out.println(people);
        Page<People> currentPage = peopleService.getSomePeople(pageno, uid,people);
        System.out.println("cp"+currentPage);
        System.out.println("cpd"+currentPage.getDatas());
        session.setAttribute("page",currentPage);
        return "userManagement";
    }

    @RequestMapping("/getPage")
    public String page(int pageno,HttpSession session){
        Page<People> currentPage = peopleService.getCurrentPage(pageno, uid);
        session.setAttribute("page",currentPage);
        return "userManagement";
    }

}

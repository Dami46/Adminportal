package com.adminportal.Controler;

import com.adminportal.Domain.BalanceRequest;
import com.adminportal.Domain.User;
import com.adminportal.Service.BalanceService;
import com.adminportal.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private BalanceService balanceService;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/recharges")
    public String recharges(Model model) {
        List<BalanceRequest> requestList = balanceService.findAll();
        List<User> userList = new ArrayList<>();

        for (BalanceRequest balanceRequest : requestList) {
            userList.add(userService.findById(balanceRequest.getUser().getId()));
        }
        model.addAttribute("userList", userList);
        model.addAttribute("requestList", requestList);

        return "recharges";
    }


    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}

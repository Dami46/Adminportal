package com.adminportal.Controler;

import com.adminportal.Domain.BalanceRequest;
import com.adminportal.Domain.User;
import com.adminportal.Service.BalanceService;
import com.adminportal.Service.UserService;
import com.adminportal.Utility.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailConstructor mailConstructor;
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

    @RequestMapping("/login")
    public String login() {
        return "login";
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

    @RequestMapping(value = "/submitRequest", method = RequestMethod.POST)
    public String submitRequest(@ModelAttribute("id") String id, HttpServletRequest request,
                                Model model) {
        BalanceRequest balanceRequest = balanceService.findById(Long.parseLong(id.substring(8)));
        User user = balanceRequest.getUser();

        user.setBalance(user.getBalance() + balanceRequest.getSumToAdd());
        balanceService.removeOne(balanceRequest.getId());

        SimpleMailMessage email = mailConstructor.constructAcceptBalanceRequestEmail(request.getLocale(), user);
        mailSender.send(email);

        model.addAttribute("emailSent", "true");
        return "redirect:/recharges";
    }

    @RequestMapping(value = "/removeRequest", method = RequestMethod.POST)
    public String removeRequest(@ModelAttribute("id") String id, HttpServletRequest request, Model model) {

        BalanceRequest balanceRequest = balanceService.findById(Long.parseLong(id.substring(8)));
        balanceService.removeOne(balanceRequest.getId());
        List<BalanceRequest> requestList = balanceService.findAll();
        User user = balanceRequest.getUser();

        SimpleMailMessage email = mailConstructor.constructRejectBalanceRequestEmail(request.getLocale(), user);
        mailSender.send(email);

        model.addAttribute("emailSent", "true");
        model.addAttribute("requestList", requestList);
        return "redirect:/recharges";
    }

}

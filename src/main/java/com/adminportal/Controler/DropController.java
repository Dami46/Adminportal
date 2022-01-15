package com.adminportal.Controler;

import com.adminportal.Domain.Book;
import com.adminportal.Domain.DropItem;
import com.adminportal.Domain.User;
import com.adminportal.Domain.UserToDrop;
import com.adminportal.Dto.RollDropDto;
import com.adminportal.Service.BookService;
import com.adminportal.Service.DropService;
import com.adminportal.Service.UserService;
import com.adminportal.Utility.MailConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/drop")
public class DropController {

    @Autowired
    private DropService dropService;

    @Autowired
    private BookService bookService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private UserService userService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/dropList")
    public String dropList(Model model) {

        List<DropItem> itemToDropList = dropService.findAll();

        if (itemToDropList.isEmpty()) {
            model.addAttribute("emptyList", true);
            return "dropList";
        }

        model.addAttribute("itemToDropList", itemToDropList);

        return "dropList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addDrop(Model model) {
        DropItem dropItem = new DropItem();
        List<Book> books = bookService.findAllForDrop();

        LocalDate todayDate = LocalDate.now();

        model.addAttribute("todayDate", todayDate);
        model.addAttribute("books", books);
        model.addAttribute("dropItem", dropItem);
        return "addDrop";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addDropPost(@ModelAttribute("dropItem") DropItem dropItem, HttpServletRequest request) {

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            Date date = formatter.parse(dropItem.getRollDate());
            dropItem.setRollDate(date.toString());

            Date signingDate = formatter.parse(dropItem.getSigningDate());
            dropItem.setSigningDate(signingDate.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dropService.save(dropItem);
        return "redirect:dropList";
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public String startDrop(@ModelAttribute("id") String id) {

        Optional<DropItem> optionalDropItem = dropService.findById(Long.parseLong(id.substring(8)));

        if (optionalDropItem.isPresent()) {
            DropItem dropItem = optionalDropItem.get();
            dropItem.setWasStarted(true);
            dropItem.setSigningDate(new Date().toString());

            dropService.save(dropItem);
        }

        return "forward:/dropList";
    }

    @RequestMapping(value = "/startDrop", method = RequestMethod.POST)
    public ResponseEntity<?> timerStartDrop(HttpServletRequest request) throws IOException {

        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        RollDropDto rollDropDto = objectMapper.readValue(requestBody, RollDropDto.class);

        Optional<DropItem> optionalDropItem = dropService.findById(Long.valueOf(rollDropDto.getDropId()));

        if (optionalDropItem.isPresent()) {
            DropItem dropItem = optionalDropItem.get();
            dropItem.setWasStarted(true);
            dropItem.setSigningDate(new Date().toString());

            dropService.save(dropItem);

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/roll", method = RequestMethod.POST)
    public String  rollDrop(@ModelAttribute("id") String id, HttpServletRequest request,
                                      Model model) {

        Optional<DropItem> optionalDropItem = dropService.findById(Long.parseLong(id.substring(8)));

        if (optionalDropItem.isPresent()) {
            DropItem dropItem = optionalDropItem.get();
            dropItem.setWasRolled(true);
            dropItem.setRollDate(new Date().toString());

            dropService.save(dropItem);

            int bookCounter = dropItem.getBook().getInStockNumber();

            for (UserToDrop userToDrop : dropItem.getUserToDropList()) {
                User user = userToDrop.getUser();

                if (user.getBalance() < Objects.requireNonNull(dropItem.getBook()).getOurPrice() || bookCounter == 0) {
                    model.addAttribute("insufficientUserBalance", true);

                    mailSender.send(mailConstructor.constructDropLoserEmail(userToDrop.getUser(), dropItem));

                } else {
                    model.addAttribute("insufficientUserBalance", false);

                    user.setBalance(Math.round((user.getBalance() - dropItem.getBook().getOurPrice()) * 100.0) / 100.0);
                    userService.save(user);

                    mailSender.send(mailConstructor.constructDropWinnerEmail(userToDrop.getUser(), dropItem));
                    bookCounter = bookCounter - 1;
                }
            }
        }

        model.addAttribute("emailSent", "true");
        return "forward:/dropList";
    }

    @RequestMapping(value = "/rollDrop", method = RequestMethod.POST)
    public ResponseEntity<?> timerRollDrop(HttpServletRequest request,
                                           Model model) throws IOException {

        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        RollDropDto rollDropDto = objectMapper.readValue(requestBody, RollDropDto.class);

        Optional<DropItem> optionalDropItem = dropService.findById(Long.valueOf(rollDropDto.getDropId()));

        if (optionalDropItem.isPresent()) {
            DropItem dropItem = optionalDropItem.get();
            dropItem.setWasRolled(true);
            dropItem.setRollDate(new Date().toString());

            dropService.save(dropItem);

            int bookCounter = dropItem.getBook().getInStockNumber();

            for (UserToDrop userToDrop : dropItem.getUserToDropList()) {
                User user = userToDrop.getUser();

                if (user.getBalance() < Objects.requireNonNull(dropItem.getBook()).getOurPrice() || bookCounter == 0) {
                    model.addAttribute("insufficientUserBalance", true);

                    mailSender.send(mailConstructor.constructDropLoserEmail(userToDrop.getUser(), dropItem));

                } else {
                    model.addAttribute("insufficientUserBalance", false);

                    user.setBalance(Math.round((user.getBalance() - dropItem.getBook().getOurPrice()) * 100.0) / 100.0);
                    userService.save(user);

                    mailSender.send(mailConstructor.constructDropWinnerEmail(userToDrop.getUser(), dropItem));
                    bookCounter = bookCounter - 1;
                }
            }
        }

        model.addAttribute("emailSent", "true");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/removeDrop", method = RequestMethod.POST)
    public String remove(
            @ModelAttribute("id") String id, Model model
    ) {
        dropService.removeOne(Long.parseLong(id.substring(8)));
        return "forward:/dropList";
    }

}

package com.example.MyWebSite.controller;

import com.example.MyWebSite.domain.Message;
import com.example.MyWebSite.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String gretting(Model model) {
        return "/home";
    }

    @GetMapping("/main")
    public String main(Model model) {
        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("addMessage", new Message());
        model.addAttribute("messages", messages);
        return "main";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("addMessage", new Message());
        return "/main";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("addMessage") Message message, Model model) {
        messageRepo.save(message);
        return "redirect:/main";
    }

}

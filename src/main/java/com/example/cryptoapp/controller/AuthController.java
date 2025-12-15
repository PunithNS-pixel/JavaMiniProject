package com.example.cryptoapp.controller;

import com.example.cryptoapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String root(HttpSession session) {
        return session.getAttribute("username") == null ? "redirect:/login" : "redirect:/encrypt";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        if (username == null || username.isBlank()) {
            model.addAttribute("error", "Username is required");
            return "login";
        }
        if (password == null || password.isBlank()) {
            model.addAttribute("error", "Password is required");
            return "login";
        }
        if (!userService.authenticate(username, password)) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
        session.setAttribute("username", username.trim());
        return "redirect:/encrypt";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam(value = "confirmPassword") String confirmPassword,
                           Model model) {
        if (username == null || username.isBlank()) {
            model.addAttribute("error", "Username is required");
            return "signup";
        }
        if (password == null || password.isBlank()) {
            model.addAttribute("error", "Password is required");
            return "signup";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "signup";
        }
        if (userService.userExists(username)) {
            model.addAttribute("error", "Username already exists");
            return "signup";
        }
        if (userService.register(username, password)) {
            return "redirect:/login?signup=success";
        }
        model.addAttribute("error", "Registration failed. Please try again.");
        return "signup";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

package br.edu.ifpb.pweb2.armants.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public ModelAndView getForm(ModelAndView mav) {
        mav.setViewName("login/login");
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView mav) {
        mav.setViewName("login/login?logout");
        return mav;
    }

}
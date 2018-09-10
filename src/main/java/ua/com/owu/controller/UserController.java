package ua.com.owu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.owu.models.User;
import ua.com.owu.service.MailService;
import ua.com.owu.service.userService.UserService;
import ua.com.owu.utils.UserEditor;
import ua.com.owu.utils.UserValidator;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserEditor userEditor;

    @Autowired
    UserValidator userValidator;
    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }





    @PostMapping("/save")
    public String save(User user,
                       BindingResult bindingResult,
                       Model model
    ) throws IOException {
        String username = user.getUsername();
        System.out.println(username);
        String path = System.getProperty("user.dir")
                + File.separator
                + "src"
                + File.separator
                + "main"
                + File.separator
                + "resources"
                + File.separator
                + "static"
                + File.separator;


        userValidator.validate(user,bindingResult);

        if (bindingResult.hasErrors()) {

            String errorMessage = "";
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError allError : allErrors) {
                String code = allError.getCode();
                errorMessage += " " + environment.getProperty(code);
            }

            model.addAttribute("error" , errorMessage);
            return "index";
        }


        try {
            mailService.sendConfirmMessage(user.getEmail(), user);
        } catch (MessagingException e) {
            e.printStackTrace();

        }
        userEditor.setValue(user);
        userService.save(user);


        return "redirect:/login";
    }

}

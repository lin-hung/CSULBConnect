package spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import spring.models.Signup;
import spring.services.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

@Controller
public class MainController {
    private Logger logger= Logger.getLogger(MainController.class.getName());
    @ Autowired
    public UserService userService;

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mav = new ModelAndView("index");

//        mav.addObject("", new Login());

        return mav;

    }
    @RequestMapping(value="/login", method= RequestMethod.GET)
    public ModelAndView reg(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("login");
        return mav;

    }
    @RequestMapping(value="/reg", method= RequestMethod.POST)
    public ModelAndView regSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Signup signup=new Signup();
        String email=request.getParameter("regEmail").toLowerCase();
        signup.setsEmail(email);
        signup.setsFirstName(request.getParameter("regName"));
        signup.setsLastName(request.getParameter("regLName"));
        signup.setsPassword(request.getParameter("regPass"));
        System.out.print(signup.toString());
        ModelAndView mav = new ModelAndView("registerconf");
        try {
            userService.register(signup);
        } catch (Exception e) {
            response.sendError(500);
        }
        return mav;
    }

    @RequestMapping(value="/conf", method= RequestMethod.GET)
    public ModelAndView confirm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mav = new ModelAndView("registerconf");
        return mav;
    }

    @RequestMapping(value="/regConfirm", method=RequestMethod.POST)
    public ModelAndView response(HttpServletRequest request, HttpServletResponse response) throws IOException {
       String confirmationNumberStr=(request.getParameter("cCode"));
      String confirmationEmail=request.getParameter("cEmail");
        logger.warning(confirmationNumberStr+confirmationEmail);
        int confirmationNumber=Integer.parseInt(confirmationNumberStr);

        try {
            userService.confirm(confirmationEmail,confirmationNumber);
        } catch (Exception e) {
            logger.warning("something went horribly, horribly wrong");
            e.printStackTrace();
        ModelAndView mav=new ModelAndView("blank");
        mav.addObject("message","<h3 class=\"col text-center\">Please refresh and try again.</h3>");
        return mav;
        }
        ModelAndView mav=new ModelAndView("blank");
        mav.addObject("message","<h3 class=\"col text-center\">Success! You may now sign in.</h3>");
        return mav;
    }
    @RequestMapping(value="/sectest", method= RequestMethod.GET)
    public ModelAndView st(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("registerconf");
        return mav;
    }
    @RequestMapping(value="/sectestpub", method= RequestMethod.GET)
    public ModelAndView stp(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("registerconf");
        return mav;
    }
//    @RequestMapping(value="/testregistered", method= RequestMethod.GET)
//    public ModelAndView treg(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        ModelAndView mav = new ModelAndView("registerconf");
//        try {
//            userService.isRegistered("abc@abc");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return mav;
//    }

}

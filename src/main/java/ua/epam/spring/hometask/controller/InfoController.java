package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.UserDiscountInfo;
import ua.epam.spring.hometask.security.AuthorizedUser;
import ua.epam.spring.hometask.service.info.InfoService;
import ua.epam.spring.hometask.service.user.UserService;

/**
 * Created by Viktor_Skapoushchenk on 4/23/2018.
 */
@Controller
@RequestMapping(value = "/info")
public class InfoController {

    private InfoService infoService;
    private UserService userService;

    @Autowired
    public InfoController(InfoService infoService, UserService userService) {
        this.infoService = infoService;
        this.userService = userService;
    }

    @GetMapping
    public String getUserDiscountsInfo(ModelMap modelMap){
        UserDetails userDetails = (AuthorizedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        UserDiscountInfo info = infoService.getByUser(user);
        modelMap.put("info", info.getDiscountMap());
        return "info";
    }
}

package karstenroethig.laeufe.controller;

import karstenroethig.laeufe.controller.util.UrlMappings;
import karstenroethig.laeufe.controller.util.ViewEnum;
import karstenroethig.laeufe.dto.info.AccountInfoDto;
import karstenroethig.laeufe.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@ComponentScan
@Controller
@RequestMapping( UrlMappings.CONTROLLER_USER )
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(
        value = "/account-info",
        method = RequestMethod.GET
    )
    public String accountInfo( Model model ) {
    	
    	AccountInfoDto accountInfo = userService.getAccountInfo();

        model.addAttribute( "accountInfo", accountInfo );

        return ViewEnum.USER_ACCOUNT_INFO.getViewName();
    }
}

package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.User;
import web.service.UserService;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

	@GetMapping("user")
	public String userLoginPage() {
		return "user";
	}

	@GetMapping("show")
	public String userPage(Model model, Principal principal) {
		User currentUser = userService.getUserByUsername(principal.getName());
		model.addAttribute("user", currentUser);
        return "show";
	}
}
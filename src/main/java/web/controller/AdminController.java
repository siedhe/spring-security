package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping("/admin/edit")
    public String editUser(@RequestParam long id, ModelMap model) {
        User user = userService.readUserById(id);
        model.addAttribute("user", user);
        Role[] roles = user.getRoles().toArray(new Role[0]);
        if(roles.length == 1) {
            model.addAttribute("id1", roles[0].getId());
        } else if(roles.length == 2) {
            if (roles[0].getRole().equals("ROLE_USER")) {
                model.addAttribute("id1", roles[0].getId());
                model.addAttribute("id2", roles[1].getId());
            } else if (roles[0].getRole().equals("ROLE_ADMIN")){
                model.addAttribute("id1", roles[1].getId());
                model.addAttribute("id2", roles[0].getId());
            }
        }
        return "edit";
    }

    @PostMapping("/admin/edit")
    public String updateUser(@ModelAttribute User user, @RequestParam(value = "newPassword", required = false) String newPassword, @RequestParam(name = "roleAdmin", required = false) String roleAdmin,
                             @RequestParam(name = "roleUser", required = false) String roleUser,
                             Model model) {
        try {
            if (!newPassword.isEmpty()) {
                user.setPassword(newPassword);
            }

            Set<Role> userRoles = new HashSet<>();
            if (roleAdmin != null) {
                userRoles.add(roleService.getRoleByName(roleAdmin));
            }
            if (roleUser != null) {
                userRoles.add(roleService.getRoleByName(roleUser));

            }
            user.setRoles(userRoles);
            userService.updateUser(user);

            return "redirect:/index";
        } catch (Throwable ex) {
            return "error";
        }
    }

    @GetMapping("/admin/new")
    public String addUser() {
        return "new";
    }

    @PostMapping("/admin/new")
    public String saveUser(User user, @RequestParam(value = "roleAdmin", required = false) String roleAdmin,
                           @RequestParam(name = "roleUser", required = false) String roleUser,
                           Model model){
        Set<Role> userRoles = new HashSet<>();
        if (roleAdmin != null) {
            userRoles.add(roleService.getRoleByName(roleAdmin));
        }
        if (roleUser != null) {
            userRoles.add(roleService.getRoleByName(roleUser));
        }
        user.setRoles(userRoles);

        if (!userService.addUser(user)) {
            return "error";
        }
        return "redirect:/index";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/index";
    }
}

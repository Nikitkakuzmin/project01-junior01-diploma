package kz.nikitka.diploma.project.dip.controller;

import kz.nikitka.diploma.project.dip.model.Ingredient;
import kz.nikitka.diploma.project.dip.model.Recipe;
import kz.nikitka.diploma.project.dip.model.User;
import kz.nikitka.diploma.project.dip.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final AccountService accountService;
    private final RecipeService recipeService;
    private final FileUploadService fileUploadService;
    private final PermissionService permissionService;

    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        return "register";
    }



    @GetMapping(value = "/signin")
    public String signin(Model model) {
        return "signin";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public String profile(Model model) {
        return "profile";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin")
    public String adminPanel(Model model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("permissions", userService.getPermissions());
        return "admin";
    }

    @PreAuthorize("hasAnyRole('ROLE_MODER')")
    @GetMapping(value = "/moder")
    public String moderatorPanel(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "moder";
    }

    @GetMapping(value = "/forbidden")
    public String forbidden(Model model) {
        return "forbidden";
    }

    @PostMapping(value = "/register")
    public String register(@RequestParam(name = "user_email") String email,
                           @RequestParam(name = "user_password") String password,
                           @RequestParam(name = "user_re_password") String rePassword,
                           @RequestParam(name = "user_full_name") String fullName) {
        if (accountService.registerUser(email, password, rePassword, fullName) != null) {
            return "redirect:/register?success";
        } else {
            return "redirect:/?error";
        }

    }


    @GetMapping(value = "/repassword")
    public String repassword(Model model) {
        return "repassword";
    }

    @PostMapping(value = "/repassword")
    public String updatePassword(@RequestParam(name = "user_old_password") String oldPassword,
                                 @RequestParam(name = "user_new_password") String newPassword,
                                 @RequestParam(name = "user_re_password") String repeatNewPassword) {
        if (accountService.updatePassword(oldPassword, newPassword, repeatNewPassword) != null) {
            return "redirect:/profile?passwordsuccess";
        } else {
            return "redirect:/profile?passworderror";
        }
    }

    @PostMapping(value = "/assign-role")
    public String assignIngredient(@RequestParam(name = "role_id") Long roleId,
                                   @RequestParam(name = "user_id") Long userId) {
        userService.assignRole(roleId, userId);
        return "redirect:/admin/";
    }

    @PostMapping(value = "/unassign-role")
    public String unAssignIngredient(@RequestParam(name = "role_id") Long roleId,
                                     @RequestParam(name = "user_id") Long userId) {
        userService.unassignRole(roleId, userId);
        return "redirect:/admin/";
    }


    @PostMapping(value = "/upload-ava")
    public String uploadAva(@RequestParam(name = "ava")MultipartFile file){
        fileUploadService.uploadPicture(file);
        return "redirect:/profile";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/view-ava/{url}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getPicture(@PathVariable(name = "url") String url) throws IOException {
        return fileUploadService.getAva(url);
    }







}




package com.db.finki.www.build_board.controller.home_pages;

import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.service.BBUserDetailsService;
import com.db.finki.www.build_board.service.threads.impl.FileUploadService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("{username}")
public class UserProfileController {
    private final BBUserDetailsService userService;
    private final FileUploadService fileUploadService;

    public UserProfileController(BBUserDetailsService userService, FileUploadService fileUploadService) {
        this.userService = userService;
        this.fileUploadService = fileUploadService;
    }

    @GetMapping("/profile")
    public String getProfilePage(@PathVariable String username, @SessionAttribute BBUser user, Model model) {
        try {
            model.addAttribute("user", userService.loadUserByUsername(username));
            model.addAttribute("canEdit", user.getUsername().equals(username));
            return "home_pages/profile";
        }catch(Exception ignore){
            return "redirect:/";
        }
    }

    @PreAuthorize("requestedByUsername==username")
    @PostMapping("/upload-avatar")
    public String uploadAvatar(Model model,
                               @PathVariable String username,
                               @RequestParam MultipartFile userImage,
                               RedirectAttributes redirectAttributes,
                               @RequestParam(name = "cur_user_username") String requestedByUsername
    ) {
        BBUser u = (BBUser) userService.loadUserByUsername(username);
        try{
            fileUploadService.uploadAvatar(userImage,u.getId());
            redirectAttributes.addFlashAttribute("message", "Avatar uploaded successfully");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Cant upload avatar");
            System.out.println(e.getMessage());
        }

        return "redirect:/" + username + "/profile";
    }

    @PreAuthorize("requestedByUsername==oldUsername")
    @PostMapping("/profile/change")
    public String changeInfo(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String description,
            @PathVariable(name = "username") String oldUsername,
            @RequestParam(name = "username") String newUsername,
            @RequestParam(name = "cur_user_username") String requestedByUsername,
            @RequestParam String password,
            HttpSession session
    ){
        BBUser user = userService.changeInfoForUserWithUsername(
                oldUsername,
                newUsername,
                email,
                name,
                description,
                password
        );
        session.setAttribute("user", user);
        return "redirect:/";
    }
}

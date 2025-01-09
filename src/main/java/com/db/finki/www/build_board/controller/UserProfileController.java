package com.db.finki.www.build_board.controller;

import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.service.BBUserDetailsService;
import com.db.finki.www.build_board.service.threads.impl.FileUploadService;
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
    public String getProfilePage(@PathVariable String username, Model model) {
        BBUser u = (BBUser) userService.loadUserByUsername(username);

        model.addAttribute("user", u);
        return "profile";
    }
    @PostMapping("/upload-avatar")
    public String uploadAvatar(Model model, @PathVariable String username, @RequestParam MultipartFile userImage, RedirectAttributes redirectAttributes) {
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
}

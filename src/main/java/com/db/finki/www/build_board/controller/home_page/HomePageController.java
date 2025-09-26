package com.db.finki.www.build_board.controller.home_page;

import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.service.user.BBUserDetailsService;
import com.db.finki.www.build_board.service.thread.itf.TagService;
import com.db.finki.www.build_board.service.util.NamedThreadService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomePageController {
    private final TagService tagService;
    private final BBUserDetailsService bbUserDetailsService;
    private final SecurityContextRepository securityContextRepository;
    private final NamedThreadService  namedThreadService;

    public HomePageController(TagService tagService, BBUserDetailsService bbUserDetailsService,
            SecurityContextRepository securityContextRepository, NamedThreadService namedThreadService
                             ) {
        this.tagService = tagService;
        this.bbUserDetailsService = bbUserDetailsService;
        this.securityContextRepository = securityContextRepository;
        this.namedThreadService = namedThreadService;
    }

    @GetMapping("/")
    public String search(
            @RequestParam(required = false, defaultValue = "") String query,
            @RequestParam(required = false, defaultValue = "all") String type,
            @RequestParam(required = false, defaultValue = "") List<String> filters,
            @RequestParam(required = false,name = "tags") List<String> tag,
            Model model
                        ) {

        if(type.equals("all"))
            type=null;

        String title = null;
        String content = null;

        if(query != null && filters.contains("title"))
            title= URLDecoder.decode(query, StandardCharsets.UTF_8);

        if(query != null && filters.contains("content"))
            content=URLDecoder.decode(query, StandardCharsets.UTF_8);

        if(filters.isEmpty()){
            title=URLDecoder.decode(query, StandardCharsets.UTF_8);
            content=URLDecoder.decode(query, StandardCharsets.UTF_8);
        }

        model.addAttribute("threads", namedThreadService.getAll(title,content,type,tag));
        model.addAttribute("tags",tagService.getAll());

        return "home_pages/home";
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "home_pages/project_description";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new BBUser());
        model.addAttribute("canEdit",true);
        return "home_pages/register";
    }

    @PostMapping("/register")
    public String registerPost(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String description,
            @RequestParam String sex,
            RedirectAttributes redirectAttributes,
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            Authentication authentication = bbUserDetailsService.registerUser(username, email, name, password,description, sex);

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);

            securityContextRepository.saveContext(context,request,response);

            session.setAttribute("user", authentication.getPrincipal());
            return "redirect:/";
        }catch (DataIntegrityViolationException e) {
            if(e.getMessage().contains("users_username_key"))
            {
                redirectAttributes.addFlashAttribute("duplicatedUsername",username);
                return "redirect:/register";
            }
            throw e;
        }
    }
}

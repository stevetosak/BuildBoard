package com.db.finki.www.build_board.controller.home_page;

import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.service.user.BBUserDetailsService;
import com.db.finki.www.build_board.service.search.SearchService;
import com.db.finki.www.build_board.service.thread.itf.TagService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomePageController {
    private final SearchService searchService;
    private final TagService tagService;
    private final BBUserDetailsService bbUserDetailsService;
    private final SecurityContextRepository securityContextRepository;

    public HomePageController(SearchService searchService, TagService tagService, BBUserDetailsService bbUserDetailsService, SecurityContextRepository securityContextRepository) {
        this.searchService = searchService;
        this.tagService = tagService;
        this.bbUserDetailsService = bbUserDetailsService;
        this.securityContextRepository = securityContextRepository;
    }

    @GetMapping("/")
    public String search( @RequestParam(required = false)String query, @RequestParam(required = false) List<String> filters,  @RequestParam(required = false) String type , Model model) {
        if(filters == null || filters.isEmpty()) {
            filters = new ArrayList<>();
            filters.add("all");
        }

        model.addAttribute("threads", searchService.search(query,filters,type));

        System.out.println("TAGS: " + tagService.getAll());
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
}

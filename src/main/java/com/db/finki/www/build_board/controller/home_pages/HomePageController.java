package com.db.finki.www.build_board.controller.home_pages;

import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.service.BBUserDetailsService;
import com.db.finki.www.build_board.service.search.SearchService;
import com.db.finki.www.build_board.service.thread.itfs.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomePageController {
    private final SearchService searchService;
    private final TagService tagService;
    private final BBUserDetailsService bbUserDetailsService;

    public HomePageController(SearchService searchService, TagService tagService, BBUserDetailsService bbUserDetailsService) {
        this.searchService = searchService;
        this.tagService = tagService;
        this.bbUserDetailsService = bbUserDetailsService;
    }

    @GetMapping("/")
    public String search( @RequestParam(required = false)String query, @RequestParam(required = false) List<String> filters,  @RequestParam(required = false) String type , Model model) {
        if(filters == null || filters.isEmpty()) {
            filters = new ArrayList<>();
            filters.add("all");
        }

        model.addAttribute("threads", searchService.search(query,filters,type));

        System.out.println("TAGS: " + tagService.findAll());
        model.addAttribute("tags",tagService.findAll());
        return "home_pages/home";
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "home_pages/project_description";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
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
            @RequestParam String sex
    ) {
        bbUserDetailsService.createUser(
                username,
                email,
                name,
                password,
                description,
                sex
        );
        return "redirect:/login";
    }
}

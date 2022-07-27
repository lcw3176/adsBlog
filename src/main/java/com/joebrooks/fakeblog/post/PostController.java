package com.joebrooks.fakeblog.post;

import lombok.RequiredArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/")
    public String showMainPage(Model model) throws IOException {
        model.addAttribute("postList", postService.getPostList());

        return "index";
    }

    @GetMapping("/post/{page}")
    public String markdownView(@PathVariable("page") String page, Model model) throws Exception {

        model.addAttribute("contents", postService.getMarkdownValueFormLocal(page));

        return "fragment/post";
    }

}

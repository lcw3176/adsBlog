package com.joebrooks.fakeblog.post;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@Service
public class PostService {

    private final String LOCAL_MANUAL_PATH = "static/post/";
    private final int startPage = 1;
    private final int lastPage = 9;

    public String getMarkdownValueFormLocal(String page) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        ClassPathResource classPathResource = new ClassPathResource(LOCAL_MANUAL_PATH + page);

        BufferedReader br = Files.newBufferedReader(Paths.get(classPathResource.getURI()));
        br.lines().forEach(line -> stringBuilder.append(line).append("\n"));

        String markdownValueFormLocal = stringBuilder.toString();

        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdownValueFormLocal);
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        return renderer.render(document);
    }

    public List<Post> getPostList() throws IOException {
        List<Post> postLst = new LinkedList<>();
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        for(int i = startPage; i <= lastPage; i++){
            ClassPathResource classPathResource = new ClassPathResource(LOCAL_MANUAL_PATH + i);

            BufferedReader br = Files.newBufferedReader(Paths.get(classPathResource.getURI()));
            String strTitle = br.lines().limit(1).findFirst().orElseThrow(() -> {
                throw new RuntimeException("페이지 없음");
            });

            Node title = parser.parse(strTitle);

            postLst.add(Post.builder()
                    .url(i)
                    .title(renderer.render(title))
                    .build());
        }

        return postLst;
    }
}

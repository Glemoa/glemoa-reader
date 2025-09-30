package hyunsub.glemoareader.controller;

import hyunsub.glemoareader.dto.PostDto;
import hyunsub.glemoareader.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<PostDto> readPost(String source) {
        return postService.readPost(source);
    }
}

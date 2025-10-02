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

    @GetMapping("/recent-posts")
    public List<PostDto> readRecentPost(String source) {
        return postService.readRecentPost(source);
    }

    @GetMapping("/today-recommended-posts")
    public List<PostDto> readTodayRecommendedPosts(String source) {
        return postService.readTodayRecommendedPosts(source);
    }

    @GetMapping("/today-view-count-posts")
    public List<PostDto> readTodayViewCountPosts(String source) {
        return postService.readTodayViewCountPosts(source);
    }
}

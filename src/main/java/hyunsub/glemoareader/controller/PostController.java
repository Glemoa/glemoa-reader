package hyunsub.glemoareader.controller;

import hyunsub.glemoareader.document.PostDocument;
import hyunsub.glemoareader.dto.PostDto;
import hyunsub.glemoareader.dto.PostPageResDto;
import hyunsub.glemoareader.service.PostSearchElasticService;
import hyunsub.glemoareader.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;
    private final PostSearchElasticService postSearchElasticService;

    public PostController(PostService postService, PostSearchElasticService postSearchElasticService) {
        this.postService = postService;
        this.postSearchElasticService = postSearchElasticService;
    }

    @GetMapping("/post/{postId}")
    public PostDto findByPostId (@PathVariable("postId") Long postId) {
        return postService.findById(postId);
    }

    @PostMapping("/post/viewBookMarkedPost")
    public List<PostDto> viewBookMarkedPostByPostIdList(@RequestBody List<Long> postIdList) {
        return postService.viewBookMarkedPostByPostIdList(postIdList);
    }

    @GetMapping("/recent-posts")
    public PostPageResDto readRecentPostPaginated(
            @RequestParam("source") String source,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize,
            @RequestParam("movablePageCount") Long movablePageCount
    ) {
        return postService.readRecentPostPaginated(source, page, pageSize, movablePageCount);
    }

    @GetMapping("/today-recommended-posts")
    public PostPageResDto readTodayRecommendedPosts(
            @RequestParam("source") String source,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize,
            @RequestParam("movablePageCount") Long movablePageCount
    ) {
        return postService.readTodayRecommendedPostsPaginated(source, page, pageSize, movablePageCount);
    }

    @GetMapping("/today-view-count-posts")
    public PostPageResDto readTodayViewCountPosts(
            @RequestParam("source") String source,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize,
            @RequestParam("movablePageCount") Long movablePageCount
    ) {
        return postService.readTodayViewCountPostsPaginated(source, page, pageSize, movablePageCount);
    }

    @GetMapping("/search-posts")
    public PostPageResDto searchPosts(
            @RequestParam("keyword") String keyword,
            @RequestParam("source") String source,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize,
            @RequestParam("movablePageCount") Long movablePageCount
    ) {
//        return postService.searchPosts(keyword, source, page, pageSize, movablePageCount);
        return postSearchElasticService.searchPosts(keyword, source, page, pageSize, movablePageCount);
    }

    @GetMapping("/search-today-recommended-posts")
    public PostPageResDto searchTodayRecommendedPosts(
            @RequestParam("keyword") String keyword,
            @RequestParam("source") String source,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize,
            @RequestParam("movablePageCount") Long movablePageCount
    ) {
//        return postService.searchTodayRecommendedPostsPaginated(keyword, source, page, pageSize, movablePageCount);
        return postSearchElasticService.searchTodayRecommendedPosts(keyword, source, page, pageSize, movablePageCount);
    }

    @GetMapping("/search-today-view-count-posts")
    public PostPageResDto searchTodayViewCountPosts(
            @RequestParam("keyword") String keyword,
            @RequestParam("source") String source,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize,
            @RequestParam("movablePageCount") Long movablePageCount
    ) {
//        return postService.searchTodayViewCountPostsPaginated(keyword, source, page, pageSize, movablePageCount);
        return postSearchElasticService.searchTodayViewCountPosts(keyword, source, page, pageSize, movablePageCount);
    }
}

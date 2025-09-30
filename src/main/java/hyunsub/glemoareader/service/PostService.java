package hyunsub.glemoareader.service;

import hyunsub.glemoareader.domain.Post;
import hyunsub.glemoareader.dto.PostDto;
import hyunsub.glemoareader.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostDto> readPost(String source) {
        Pageable pageable = PageRequest.of(0, 10);

        List<Post> postLists = postRepository.findBySourceWithLimit(source, pageable);

        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : postLists) {
            postDtoList.add(PostDto.builder()
                    .title(post.getTitle())
                    .link(post.getLink())
                    .author(post.getAuthor())
                    .commentCount(post.getCommentCount())
                    .viewCount(post.getViewCount())
                    .createdAt(post.getCreatedAt())
                    .recommendationCount(post.getRecommendationCount())
                    .source(post.getSource())
                    .build());
        }

        return postDtoList;
    }
}

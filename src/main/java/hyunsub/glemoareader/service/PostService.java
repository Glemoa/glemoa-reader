package hyunsub.glemoareader.service;

import hyunsub.glemoareader.domain.Post;
import hyunsub.glemoareader.dto.PostDto;
import hyunsub.glemoareader.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .link(post.getLink())
                .author(post.getAuthor())
                .commentCount(post.getCommentCount())
                .viewCount(post.getViewCount())
                .createdAt(post.getCreatedAt())
                .recommendationCount(post.getRecommendationCount())
                .source(post.getSource())
                .build();

        return postDto;
    }

    public List<PostDto> viewBookMarkedPostByPostIdList(List<Long> postIdList) {
        // 1. PostRepository의 findAllById(Iterable<ID> ids) 메서드를 사용해 ID 리스트로 DB 조회
        // 이 방법은 쿼리를 한 번만 실행하여 N+1 문제를 방지합니다.
        List<Post> posts = postRepository.findAllById(postIdList);

        // 2. Stream API를 사용하여 Post 엔티티 리스트를 PostDto 리스트로 변환
        // (findById 메서드의 DTO 변환 로직을 재사용하는 것이 좋습니다.)
        return posts.stream()
                .map(post -> PostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .link(post.getLink())
                        .author(post.getAuthor())
                        .commentCount(post.getCommentCount())
                        .viewCount(post.getViewCount())
                        .createdAt(post.getCreatedAt())
                        .recommendationCount(post.getRecommendationCount())
                        .source(post.getSource())
                        .build())
                .collect(Collectors.toList());
    }


    public List<PostDto> readRecentPost(String source) {
        Pageable pageable = PageRequest.of(0, 10);

        List<Post> postLists = postRepository.findBySourceWithLimit(source, pageable);

        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : postLists) {
            postDtoList.add(PostDto.builder()
                    .id(post.getId())
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

    public List<PostDto> readTodayRecommendedPosts(String source) {
        Pageable pageable = PageRequest.of(0, 10);

        List<Post> postLists = postRepository.findTodayRecommendedPosts(source, LocalDate.now().atStartOfDay(), pageable);

        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : postLists) {
            postDtoList.add(PostDto.builder()
                    .id(post.getId())
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

    public List<PostDto> readTodayViewCountPosts(String source) {
        Pageable pageable = PageRequest.of(0, 10);

        List<Post> postLists = postRepository.findTodayViewCountPosts(source, LocalDate.now().atStartOfDay(), pageable);

        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : postLists) {
            postDtoList.add(PostDto.builder()
                    .id(post.getId())
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

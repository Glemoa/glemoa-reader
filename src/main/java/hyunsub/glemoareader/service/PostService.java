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
        // findAllById는 ID 순서를 보장하지 않으므로, 애플리케이션 레벨에서 순서를 맞춰줘야 합니다.
        List<Post> posts = postRepository.findAllById(postIdList);

        // ID를 키로, Post 객체를 값으로 하는 맵을 생성하여 빠른 조회를 가능하게 합니다.
        java.util.Map<Long, Post> postMap = posts.stream()
                .collect(Collectors.toMap(Post::getId, post -> post));

        /*
           1. postIdList.stream(): 파라미터로 받은, 북마크 생성 시간순으로 이미 정렬되어 있는 ID
              목록을 순서대로 스트림으로 만듭니다.
           2. .map(postMap::get): 스트림의 각 ID를 사용하여 postMap에서 해당 ID에 맞는 Post 객체를
              찾습니다. 스트림은 순서를 유지하므로, postIdList의 순서대로 Post 객체가 매핑됩니다.
           3. .collect(Collectors.toList()): 순서대로 매핑된 Post 객체들을 새로운
              리스트(sortedPosts)로 만듭니다.
         */
        List<Post> sortedPosts = postIdList.stream()
                .map(postMap::get)
                .filter(java.util.Objects::nonNull) // 혹시 모를 null 값(삭제된 게시물 등)을 필터링합니다.
                .collect(Collectors.toList());

        // 정렬된 Post 리스트를 PostDto 리스트로 변환합니다.
        return sortedPosts.stream()
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

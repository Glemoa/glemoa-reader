package hyunsub.glemoareader.dto;

import hyunsub.glemoareader.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String link;
    private String author;
    private Integer commentCount;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private Integer recommendationCount;
    private String source;

    public static PostDto fromPost(Post post) {
        return PostDto.builder()
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
    }
}

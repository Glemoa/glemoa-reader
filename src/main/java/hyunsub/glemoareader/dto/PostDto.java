package hyunsub.glemoareader.dto;

import hyunsub.glemoareader.document.PostDocument;
import hyunsub.glemoareader.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public static PostDto fromPostDocument(PostDocument doc) {
        if (doc == null) return null;

        LocalDateTime createdAt = null;
        try {
            if (doc.getCreatedAt() != null)
                createdAt = LocalDateTime.parse(doc.getCreatedAt(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        } catch (Exception e) {
            // 실패해도 무시
        }

        return PostDto.builder()
                .id(Long.valueOf(doc.getId()))
                .title(doc.getTitle())
                .author(doc.getAuthor())
                .source(doc.getSource())
                .link(doc.getLink())
                .commentCount(doc.getCommentCount())
                .viewCount(doc.getViewCount())
                .recommendationCount(doc.getRecommendationCount())
                .createdAt(createdAt)
                .build();
    }
}

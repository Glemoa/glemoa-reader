package hyunsub.glemoareader.repository;

import hyunsub.glemoareader.document.PostDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.Instant;
import java.time.LocalDateTime;

public interface PostSearchRepository extends ElasticsearchRepository<PostDocument, Long> {

    /**
     * 1️⃣ 기본 검색 (source + title keyword)
     * - source가 일치하고
     * - title에 keyword가 포함된 게시글을
     * - 최신순(createdAt DESC, id DESC)으로 정렬
     */
    @Query("""
    {
      "bool": {
        "must": [
          { "match": { "source": "?1" }},
          { "match_phrase": { "title": "?0" }}
        ]
      }
    }
    """)
    Page<PostDocument> searchPostsPaginated(String keyword, String source, Pageable pageable);


    /**
     * 2️⃣ 오늘 게시글 중 추천순 정렬
     * - source 일치
     * - createdAt >= startOfDay
     * - title에 keyword 포함
     * - 추천순 (recommendationCount DESC)
     */
    @Query("""
    {
      "bool": {
        "must": [
          { "match": { "source": "?1" }},
          { "match_phrase": { "title": "?0" }},
          { "range": { "createdAt": { "gte": "?2" }}}
        ]
      }
    }
    """)
    Page<PostDocument> searchTodayRecommendedPostsPaginated(
            String keyword,
            String source,
            Instant startOfDay,  // String → LocalDateTime
            Pageable pageable
    );


    /**
     * 3️⃣ 오늘 게시글 중 조회순 정렬
     * - source 일치
     * - createdAt >= startOfDay
     * - title에 keyword 포함
     * - 조회순 (viewCount DESC)
     */
    @Query("""
    {
      "bool": {
        "must": [
          { "match": { "source": "?1" }},
          { "match_phrase": { "title": "?0" }},
          { "range": { "createdAt": { "gte": "?2" }}}
        ]
      }
    }
    """)
    Page<PostDocument> searchTodayViewCountPostsPaginated(
            String keyword,
            String source,
            Instant startOfDay,  // String → LocalDateTime
            Pageable pageable
    );

}

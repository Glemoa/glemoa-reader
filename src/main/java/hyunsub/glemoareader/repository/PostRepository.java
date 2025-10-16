package hyunsub.glemoareader.repository;

import hyunsub.glemoareader.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.source = :source ORDER BY p.createdAt DESC")
    List<Post> findBySourceWithLimit(@Param("source") String source, Pageable pageable);

    @Query(
            value = "SELECT p.* " +
                    "FROM (" +
                    "SELECT id FROM post " +
                    "WHERE source = :source " +
                    "ORDER BY created_at DESC, source_id DESC " +
                    "LIMIT :limit OFFSET :offset" + // :offset을 새로 정의
                    ") t LEFT JOIN post p ON t.id = p.id", // 외부 post 테이블의 별칭을 p로 지정
            nativeQuery = true
    )
    List<Post> findRecentPagePostsPaginated(
            @Param("source") String source,
            @Param("offset") Long offset,
            @Param("limit") Long limit);

    @Query("SELECT p FROM Post p WHERE p.source = :source AND p.createdAt >= :startOfDay ORDER BY p.recommendationCount DESC")
    List<Post> findTodayRecommendedPosts(@Param("source") String source, @Param("startOfDay") LocalDateTime startOfDay, Pageable pageable);

    @Query(
            value = "SELECT p.* " +
                    "FROM (" +
                    "SELECT id FROM post " +
                    "WHERE source = :source AND created_at >= :startOfDay " + // 필터링 조건 추가
                    "ORDER BY recommendation_count DESC, id DESC " + // 정렬 기준 변경 및 2차 정렬 추가
                    "LIMIT :limit OFFSET :offset" +
                    ") t LEFT JOIN post p ON t.id = p.id",
            nativeQuery = true
    )
    List<Post> findTodayRecommendedPostsPaginated(
            @Param("source") String source,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("offset") Long offset,
            @Param("limit") Long limit);

    @Query("SELECT p FROM Post p WHERE p.source = :source AND p.createdAt >= :startOfDay ORDER BY p.viewCount DESC")
    List<Post> findTodayViewCountPosts(@Param("source") String source, @Param("startOfDay") LocalDateTime startOfDay, Pageable pageable);

    @Query(
            value = "SELECT p.* " +
                    "FROM (" +
                    "SELECT id FROM post " +
                    "WHERE source = :source AND created_at >= :startOfDay " + // 필터링 조건 추가
                    "ORDER BY view_count DESC, id DESC " + // 정렬 기준 변경 및 2차 정렬 추가
                    "LIMIT :limit OFFSET :offset" +
                    ") t LEFT JOIN post p ON t.id = p.id",
            nativeQuery = true
    )
    List<Post> findTodayViewCountPostsPaginated(
            @Param("source") String source,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("offset") Long offset,
            @Param("limit") Long limit);

    @Query(
            value = "SELECT p.* " +
                    "FROM (" +
                    "SELECT id FROM post " +
                    "WHERE source = :source AND title LIKE %:keyword% " +
                    "ORDER BY created_at DESC, id DESC " +
                    "LIMIT :limit OFFSET :offset" +
                    ") t LEFT JOIN post p ON t.id = p.id",
            nativeQuery = true
    )
    List<Post> searchPostsPaginated(
            @Param("keyword") String keyword,
            @Param("source") String source,
            @Param("offset") Long offset,
            @Param("limit") Long limit);

    @Query(
            value = "select count(*) from (" +
                    "   select id from post where source = :source limit :limit" +
                    ") t",
            nativeQuery = true
    )
    Long count(
            @Param("source") String source,
            @Param("limit") Long limit);

    @Query(
            value = "select count(*) from (" +
                    "   select id from post where source = :source AND created_at >= :startOfDay limit :limit" +
                    ") t",
            nativeQuery = true
    )
    Long count(
            @Param("source") String source,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("limit") Long limit);
}

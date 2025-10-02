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

    @Query("SELECT p FROM Post p WHERE p.source = :source AND p.createdAt >= :startOfDay ORDER BY p.recommendationCount DESC")
    List<Post> findTodayRecommendedPosts(@Param("source") String source, @Param("startOfDay") LocalDateTime startOfDay, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.source = :source AND p.createdAt >= :startOfDay ORDER BY p.viewCount DESC")
    List<Post> findTodayViewCountPosts(@Param("source") String source, @Param("startOfDay") LocalDateTime startOfDay, Pageable pageable);
}

package hyunsub.glemoareader.service;

import hyunsub.glemoareader.document.PostDocument;
import hyunsub.glemoareader.dto.PostDto;
import hyunsub.glemoareader.dto.PostPageResDto;
import hyunsub.glemoareader.repository.PostSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostSearchElasticService {
    private final PostSearchRepository postSearchRepository;

    /**
     * 일반 검색 (createdAt DESC)
     */
    public PostPageResDto searchPosts(String keyword, String source, Long page, Long pageSize, Long movablePageCount) {
        Pageable pageable = PageRequest.of(page.intValue() - 1, pageSize.intValue(),
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        Page<PostDocument> pageResult = postSearchRepository.searchPostsPaginated(keyword, source, pageable);
//        log.info("Elastic 결과 개수: {}", pageResult.getTotalElements());
//        pageResult.forEach(doc -> log.info("문서: {}", doc));

        List<PostDto> dtoList = pageResult.getContent().stream()
                .map(PostDto::fromPostDocument)
                .toList();

        return PostPageResDto.builder()
                .postDtoList(dtoList)
                .postCount(pageResult.getTotalElements())
                .build();
    }

    /**
     * 오늘의 추천순 검색
     */
    public PostPageResDto searchTodayRecommendedPosts(String keyword, String source, Long page, Long pageSize, Long movablePageCount) {
        String startOfDay = LocalDateTime.now()
                .withHour(0).withMinute(0).withSecond(0).withNano(0)
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Pageable pageable = PageRequest.of(page.intValue() - 1, pageSize.intValue(),
                Sort.by(Sort.Direction.DESC, "recommendationCount"));

        Page<PostDocument> pageResult = postSearchRepository.searchTodayRecommendedPostsPaginated(keyword, source, startOfDay, pageable);
//        log.info("Elastic 결과 개수: {}", pageResult.getTotalElements());
//        pageResult.forEach(doc -> log.info("문서: {}", doc));

        List<PostDto> dtoList = pageResult.getContent().stream()
                .map(PostDto::fromPostDocument)
                .toList();

        return PostPageResDto.builder()
                .postDtoList(dtoList)
                .postCount(pageResult.getTotalElements())
                .build();
    }

    /**
     * 오늘의 조회수순 검색
     */
    public PostPageResDto searchTodayViewCountPosts(String keyword, String source, Long page, Long pageSize, Long movablePageCount) {
        String startOfDay = LocalDateTime.now()
                .withHour(0).withMinute(0).withSecond(0).withNano(0)
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Pageable pageable = PageRequest.of(page.intValue() - 1, pageSize.intValue(),
                Sort.by(Sort.Direction.DESC, "viewCount"));

        Page<PostDocument> pageResult = postSearchRepository.searchTodayViewCountPostsPaginated(keyword, source, startOfDay, pageable);
//        log.info("Elastic 결과 개수: {}", pageResult.getTotalElements());
//        pageResult.forEach(doc -> log.info("문서: {}", doc));

        List<PostDto> dtoList = pageResult.getContent().stream()
                .map(PostDto::fromPostDocument)
                .toList();

        return PostPageResDto.builder()
                .postDtoList(dtoList)
                .postCount(pageResult.getTotalElements())
                .build();
    }
}

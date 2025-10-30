package hyunsub.glemoareader.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "posts") // Elasticsearch 인덱스 이름 지정
//@Setting(settingPath = "/elasticsearch/posts-settings.json") // 이 줄을 추가
public class PostDocument {

    @Id // Elasticsearch Document의 ID
    private Long id;

    private String title;

    private String source;

    private String author;

    private String link;

    private Integer commentCount;

    // 패턴 명시: 밀리초 포함 형식 처리
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;  // String → LocalDateTime

    private Integer viewCount;

    private Integer recommendationCount;
}

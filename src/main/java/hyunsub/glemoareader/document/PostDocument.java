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

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSSSS||uuuu-MM-dd'T'HH:mm:ss||uuuu-MM-dd'T'HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Instant createdAt;  // LocalDateTime → Instant

    private Integer viewCount;

    private Integer recommendationCount;
}

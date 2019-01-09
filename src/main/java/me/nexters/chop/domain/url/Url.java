package me.nexters.chop.domain.url;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.nexters.chop.domain.BaseTime;

import javax.persistence.*;

/**
 * @author junho.park
 */
@Entity
@NoArgsConstructor
@Getter
public class Url extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "long_Url", nullable = false)
    private String longUrl;

    @Column(name = "short_Url", nullable = false, unique = true)
    private String shortUrl;

    @Column(name = "total_count")
    int totalCount;

    @Builder
    public Url(Long id, String longUrl, String shortUrl) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }
}

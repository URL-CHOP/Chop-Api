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

    @Column(name = "origin_url", nullable = false)
    private String originUrl;

    @Column(name = "short_url", nullable = false)
    private String shortUrl;

    @Builder
    public Url(Long id, String originUrl, String shortUrl) {
        this.id = id;
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
    }
}

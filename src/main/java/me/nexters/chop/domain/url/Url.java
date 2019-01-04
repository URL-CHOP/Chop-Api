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

    @Column(nullable = false)
    private String longUrl;

    @Column(nullable = false)
    private String shortUrl;

    @Builder
    public Url(Long id, String longUrl, String shortUrl) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }
}
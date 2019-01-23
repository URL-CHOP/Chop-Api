package me.nexters.chop.domain.statistics;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author taehoon.choi 2019-01-21
 */

@Entity
@Data
@Table(name = "statistics")
@Getter
@NoArgsConstructor
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "click_time")
    private String clickTime;

    private String referer;

    @Column(name = "short_url")
    private String shortUrl;

    @Builder
    public Statistics(Long id, String clickTime, String referer, String shortUrl) {
        this.id = id;
        this.clickTime = clickTime;
        this.referer = referer;
        this.shortUrl = shortUrl;
    }
}

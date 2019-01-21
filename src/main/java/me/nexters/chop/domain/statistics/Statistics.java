package me.nexters.chop.domain.statistics;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author taehoon.choi 2019-01-21
 */

@Entity
@Data
@Table(name = "statistics")
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "click_time")
    private Date clickTime;

    private String os;
    private String server;

    @Column(name = "short_url")
    private String shortUrl;
}

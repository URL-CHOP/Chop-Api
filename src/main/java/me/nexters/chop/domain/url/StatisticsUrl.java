package me.nexters.chop.domain.url;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author taehoon
 */

@Entity
@Data
@Table(name = "statistics_url")
public class StatisticsUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "url_click_time")
    private Date urlClickTime;

    @Column(name = "url_click_time_count")
    private int urlClickTimeCount;
}

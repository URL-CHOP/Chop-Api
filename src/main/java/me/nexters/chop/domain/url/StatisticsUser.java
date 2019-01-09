package me.nexters.chop.domain.url;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author taehoon
 */

@Entity
@Data
@Table(name = "statistics_user")
public class StatisticsUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_click_time")
    private Date userClickTime;

    @Column(name = "user_click_count")
    private int userClickCount;
}

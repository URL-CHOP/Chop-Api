package me.nexters.chop.domain.url;

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
@Table(name = "global_count")
public class GlobalCount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "global_count")
    int globalCount;
}

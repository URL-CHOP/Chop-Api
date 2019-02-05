package me.nexters.chop.domain.url;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.nexters.chop.domain.BaseTime;

import javax.persistence.*;

/**
 * @author junho.park
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Table(name = "global_count")
public class GlobalCount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "global_count")
    int globalCount;

    public static GlobalCount empty() {
        return GlobalCount.of(0L, 0);
    }
}

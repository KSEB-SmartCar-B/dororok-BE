package com.smartcar.dororok.member.domain.entitiy;

import com.smartcar.dororok.genre.domain.Genre;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteGenres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favoirte_genres_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public FavoriteGenres(Member member, Genre genre) {
        this.member = member;
        this.genre = genre;
    }
}

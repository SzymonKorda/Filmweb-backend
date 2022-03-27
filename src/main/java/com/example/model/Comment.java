package com.example.model;

import com.example.model.audit.DateAudit;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends DateAudit {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "film_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Long filmId;

    @NotBlank
    private String content;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}

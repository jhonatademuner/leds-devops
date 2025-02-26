package com.concursomatch.domain.exam;

import com.concursomatch.domain.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "texam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String agency;

    @Column(nullable = false)
    private String notice;

    @Column(nullable = false)
    private String code;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "texam_role",
        joinColumns = @JoinColumn(name = "exam_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

}

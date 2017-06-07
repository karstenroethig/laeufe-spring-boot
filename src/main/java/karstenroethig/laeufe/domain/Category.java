package karstenroethig.laeufe.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint( columnNames = {"id" } ),
        @UniqueConstraint( columnNames = {"name" } )
    }
)
public class Category {

    @Column(
        length = 18,
        nullable = false,
        unique = true
    )
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Id
    private Long id;

    @Column(
        length = 255,
        nullable = false
    )
    private String name;

    @Column(
        length = 1024,
        nullable = true
    )
    private String description;

    @Column( nullable = false )
    private Boolean archived;
}

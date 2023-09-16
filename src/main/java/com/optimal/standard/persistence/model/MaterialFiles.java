package com.optimal.standard.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class MaterialFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    private String name;

    private String type;

    private Long size;

    private boolean temp;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @CreationTimestamp
    private LocalDateTime dateUpdated;

    public MaterialFiles(String name, Long size, String type, boolean temp) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.temp = temp;
    }
}

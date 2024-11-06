package com.iiex.cost_share_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "AppGroups")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "userId")
    private User createdBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Expense> expenses;
}

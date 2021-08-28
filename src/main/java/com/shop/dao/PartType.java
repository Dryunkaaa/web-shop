package com.shop.dao;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "part_type")
public class PartType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "partType")
    private Set<AutoSparePart> autoSpareParts;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<AutoSparePart> getAutoSpareParts() {
        return autoSpareParts;
    }

    public void setAutoSpareParts(Set<AutoSparePart> autoSpareParts) {
        this.autoSpareParts = autoSpareParts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartType partType = (PartType) o;
        return id == partType.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

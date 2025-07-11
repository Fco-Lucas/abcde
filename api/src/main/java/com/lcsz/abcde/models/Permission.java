package com.lcsz.abcde.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "permissions")
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Boolean upload_files;
    @Column(nullable = false)
    private Boolean read_files;

    public Permission() {
    }

    public Permission(Long id, Boolean upload_files, Boolean read_files) {
        this.id = id;
        this.upload_files = upload_files;
        this.read_files = read_files;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getUpload_files() {
        return upload_files;
    }

    public void setUpload_files(Boolean upload_files) {
        this.upload_files = upload_files;
    }

    public Boolean getRead_files() {
        return read_files;
    }

    public void setRead_files(Boolean read_files) {
        this.read_files = read_files;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", upload_files=" + upload_files +
                ", read_files=" + read_files +
                '}';
    }
}

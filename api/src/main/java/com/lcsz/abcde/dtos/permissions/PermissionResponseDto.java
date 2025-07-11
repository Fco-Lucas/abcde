package com.lcsz.abcde.dtos.permissions;


public class PermissionResponseDto {
    private Long id;
    private Boolean upload_files;
    private Boolean read_files;

    public PermissionResponseDto() {
    }

    public PermissionResponseDto(Long id, Boolean upload_files, Boolean read_files) {
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
    public String toString() {
        return "PermissionResponseDto{" +
                "id=" + id +
                ", upload_files=" + upload_files +
                ", read_files=" + read_files +
                '}';
    }
}

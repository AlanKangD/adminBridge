package com.chopping.adminbridge.file.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ATTACHMENT")
@Getter
@Setter
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_no")
    private Integer fileNo;

    @Column(name = "file_original_name")
    private String fileOriginalName;

    @Column(name = "file_chage_name")
    private String fileChangeName;

    @Column(name = "file_size")
    private String fileSize;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_dt")
    private LocalDate fileDt;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_del")
    private String fileDel = "N";
}
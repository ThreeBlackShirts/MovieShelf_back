package com.blackshirts.movieshelf.entity;


import lombok.Getter;
import javax.persistence.*;

@Entity
@Getter
@Table(name = "users") // table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_nickname", nullable = false)
    private String userNickname;

    @Column(name = "user_phone", nullable = false)
    private String userPhone;

    @Column(name = "user_gender", nullable = false)
    private String userGender;

    @Column(name = "user_birth", nullable = false)
    private String userBirth;

    @Column(name = "user_register_date", nullable = false)
    private String userRegisterDate;

    @Column(name = "user_filename", nullable = false)
    private String userFilename;

    @Column(name = "user_grade", nullable = false)
    private String userGrade;




}

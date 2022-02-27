package com.blackshirts.movieshelf.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@ApiModel(value = "회원 정보", description = "아이디, 이메일, 비밀번호 등 회원 정보를 가진 Class")
@Getter
@Entity(name = "users")
public class User implements UserDetails {

    @ApiModelProperty(value = "아이디")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @ApiModelProperty(value = "이메일")
    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @ApiModelProperty(value = "비밀번호")
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @ApiModelProperty(value = "이름")
    @Column(name = "user_name", nullable = false)
    private String userName;

//    @ApiModelProperty(value = "별명")
//    @Column(name = "user_nickname", nullable = false)
//    private String userNickname;

//    @Column(name = "user_phone", nullable = false)
//    private String userPhone;

//    @ApiModelProperty(value = "가입일")
//    @Column(name = "user_register_date", nullable = false)
//    private String userRegisterDate;

//    @Column(name = "user_filename", nullable = false)
//    private String userFilename;


    @Builder
    public User(String userEmail, String userName, String userPassword) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public User() {

    }

    //Setter를 사용하지 않고 의미있는 메소드를 사용하여 변경
    public void updatePassword(String userPassword){
        this.userPassword = userPassword;
    }

//    public void updateUserNickname(String userNickname){
//        this.userNickname = userNickname;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.blackshirts.movieshelf.entity;


import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_nickname", nullable = false)
    private String userNickname;

    @Column(name = "user_phone", nullable = false)
    private String userPhone;

    @Column(name = "user_register_date", nullable = false)
    private String userRegisterDate;

    @Column(name = "user_filename", nullable = false)
    private String userFilename;

    @Column(name = "user_grade", nullable = false)
    private String userGrade;

    @Builder
    public User(String userEmail, String userPassword, String userName) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
    }

    public User() {

    }

    //Setter를 사용하지 않고 의미있는 메소드를 사용하여 변경
    public void updatePassword(String userPassword){
        this.userPassword = userPassword;
    }

    public void updateUserNickname(String userNickname){
        this.userNickname = userNickname;
    }

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

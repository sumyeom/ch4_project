package com.sparta.currency_user.dto;

import com.sparta.currency_user.config.PasswordEncoder;
import com.sparta.currency_user.entity.User;
import lombok.Getter;

@Getter
public class UserRequestDto {
    private String name;
    private String email;
    private String password;

    public User toEntity() {
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        return new User(
                this.name,
                this.email,
                passwordEncoder.encode(this.password)
        );
    }
}

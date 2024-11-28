package com.sparta.currency_user.service;

import com.sparta.currency_user.config.PasswordEncoder;
import com.sparta.currency_user.dto.UserLoginResponseDto;
import com.sparta.currency_user.dto.UserRequestDto;
import com.sparta.currency_user.dto.UserResponseDto;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.exception.CustomException;
import com.sparta.currency_user.exception.ErrorCode;
import com.sparta.currency_user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto findById(Long id) {
        return new UserResponseDto(findUserById(id));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(UserResponseDto::toDto).toList();
    }

    @Transactional
    public UserResponseDto save(UserRequestDto userRequestDto) {
        User savedUser = userRepository.save(userRequestDto.toEntity());
        return new UserResponseDto(savedUser);
    }

    /**
     * 로그인 메서드
     *
     * @param email         유저 가입한 이메일
     * @param password      유저 가입한 비밀번호
     * @return
     */
    public UserLoginResponseDto login(String email, String password) {

        // 이메일로 유저 정보 조회
        Optional<User> findUser = userRepository.findUserByEmail(email);

        // 이메일이 다르거나 비밀번호가 다른 경우
        if (findUser.isEmpty() ) {
            throw new CustomException(ErrorCode.INVALID_EMAIL);
        } else if(!passwordEncoder.matches(password,findUser.get().getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return new UserLoginResponseDto(findUser.get().getId(), findUser.get().getEmail());

    }

    @Transactional
    public void deleteUserById(Long id) {
        this.findUserById(id);
        userRepository.deleteById(id);
    }

}

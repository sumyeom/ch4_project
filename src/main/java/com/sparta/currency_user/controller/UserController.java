package com.sparta.currency_user.controller;

import com.sparta.currency_user.config.Const;
import com.sparta.currency_user.dto.UserLoginRequestDto;
import com.sparta.currency_user.dto.UserLoginResponseDto;
import com.sparta.currency_user.dto.UserRequestDto;
import com.sparta.currency_user.dto.UserResponseDto;
import com.sparta.currency_user.service.UserService;
import com.sparta.currency_user.util.ValidationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.findById(userId));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody UserRequestDto userRequestDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ValidationUtils.bindErrorMessage(bindingResult);
        }

        return ResponseEntity.ok().body(userService.save(userRequestDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
    }

    /**
     * 로그인 메서드
     *
     * @param requestDto 로그인에 필요한 유저 정보
     * @param request    유저 정보를 저장할 객체
     * @return 로그인 성공한 유저 정보
     */
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody @Valid UserLoginRequestDto requestDto, HttpServletRequest request) {

        UserLoginResponseDto responseDto = userService.login(requestDto.getEmail(), requestDto.getPassword());

        Long userId = responseDto.getId();

        HttpSession session = request.getSession();

        session.setAttribute(Const.SESSION_KEY, userId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 로그아웃 메서드
     *
     * @param request 유저 정보가 저장된 객체
     * @return 200
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}


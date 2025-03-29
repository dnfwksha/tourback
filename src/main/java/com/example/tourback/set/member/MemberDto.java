package com.example.tourback.set.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    @Email(message = "이메일 형식 xxxx@xxx.xxx으로 작성하세요.")
    private String username;
    @NotBlank(message = "패스워드를 입력하세요.")
    private String password;
    @NotBlank(message = "이름을 입력하세요.")
    private String name;
    @NotBlank(message = "전화번호를 입력하세요.")
    private String phone;

    private String role;
}

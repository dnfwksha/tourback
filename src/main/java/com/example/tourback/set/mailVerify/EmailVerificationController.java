package com.example.tourback.set.mailVerify;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/send-verification-email")
    public ResponseEntity<?> sendVerificationEmail(@RequestBody Map<String, String> request) throws AlreadyUsedException {
        String email = request.get("email");
        emailVerificationService.sendVerificationEmail(email);

        Map<String, String> response = new HashMap<>();
        response.put("message", "인증 이메일이 전송되었습니다.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestBody String emailToken) {
        var aa = emailVerificationService.existsByToken(emailToken);
        if(!aa){
            return ResponseEntity.badRequest().body("토큰이 만료되었거나 유효하지 않습니다.");
        }
        System.out.println(aa);
        boolean verified = emailVerificationService.verifyEmail(emailToken);

        Map<String, String> response = new HashMap<>();
        if (verified) {
            response.put("message", "이메일이 성공적으로 인증되었습니다.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "이메일 인증에 실패했습니다. 토큰이 만료되었거나 유효하지 않습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/check-email-verification")
    public ResponseEntity<?> checkEmailVerification(@RequestParam String email) {
        boolean isVerified = emailVerificationService.isEmailVerified(email);

        Map<String, Object> response = new HashMap<>();
        response.put("verified", isVerified);
        return ResponseEntity.ok(response);
    }
}
package com.example.tourback.set.mailVerify;

import com.example.tourback.set.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final MemberRepository memberRepository;
    private final EmailVerificationTokenRepository tokenRepository;

    private final EmailService emailService;

    @Transactional
    public void sendVerificationEmail(String email) throws AlreadyUsedException {
        boolean member = memberRepository.existsByUsername(email);
        if (member) {
            throw new AlreadyUsedException("해당 이메일은 사용한 이메일입니다.");
        }

        // 기존 토큰이 있다면 삭제
        Optional<EmailVerificationToken> existingToken = tokenRepository.findByEmail(email);
        existingToken.ifPresent(token -> tokenRepository.delete(token));

        var token = (int) (Math.random() * 9000000) + 1000000;
        var aa = UUID.randomUUID().toString();
        // 새 토큰 생성
        EmailVerificationToken newToken = new EmailVerificationToken(email,String.valueOf(token));
        tokenRepository.save(newToken);

        Map<String, String> templateData = new HashMap<>();
        templateData.put("randomCode", String.valueOf(token));

        // 인증 이메일 발송
        emailService.sendTemplateEmail(email, "dnfwksha@naver.com","CheckEmailCode", templateData);
    }

    @Transactional
    public boolean verifyEmail(String token) {
        Optional<EmailVerificationToken> tokenOptional = tokenRepository.findByToken(token);

        if (tokenOptional.isEmpty()) {
            return false;
        }

        EmailVerificationToken verificationToken = tokenOptional.get();

        if (verificationToken.isExpired()) {
            return false;
        }

        verificationToken.setVerified(true);
        tokenRepository.save(verificationToken);

        return true;
    }

    public boolean isEmailVerified(String email) {
        Optional<EmailVerificationToken> tokenOptional = tokenRepository.findByEmail(email);
        return tokenOptional.map(EmailVerificationToken::isVerified).orElse(false);
    }

    public boolean existsByToken(String emailToken) {
        return tokenRepository.existsByToken(emailToken);
    }
}
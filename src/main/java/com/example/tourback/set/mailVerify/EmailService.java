package com.example.tourback.set.mailVerify;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private final SesV2Client sesV2Client;

    public EmailService(SesV2Client sesV2Client) {
        this.sesV2Client = sesV2Client;
    }

    /**
     * SES 템플릿을 사용하여 이메일을 전송합니다.
     *
     * @param toEmail 수신자 이메일 주소
     * @param fromEmail 발신자 이메일 주소
     * @param templateName 사용할 SES 템플릿 이름
     * @param templateData 템플릿에 적용할 데이터 (JSON 형식의 문자열)
     */
    public void sendTemplateEmail(String toEmail, String fromEmail, String templateName, Map<String, String> templateData) {
        System.out.println("toEmail : "+toEmail);
        System.out.println("fromEmail : "+fromEmail);
        System.out.println("templateData : "+templateData);
        try {
            // 템플릿 데이터를 JSON 형태의 문자열로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String templateDataJson = objectMapper.writeValueAsString(templateData);
            System.out.println("templateDataJson : "+templateDataJson);

            // 이메일 수신자 설정
            Destination destination = Destination.builder()
                    .toAddresses(toEmail)
                    .build();

            // 템플릿 설정
            Template template = Template.builder()
                    .templateName(templateName)
                    .templateData(templateDataJson.toString())
                    .build();

            // 이메일 콘텐츠 설정
            EmailContent emailContent = EmailContent.builder()
                    .template(template)
                    .build();

            // 이메일 요청 생성
            SendEmailRequest emailRequest = SendEmailRequest.builder()
                    .destination(destination)
                    .content(emailContent)
                    .fromEmailAddress(fromEmail)
                    .build();

//            SendEmailRequest emailRequest = SendEmailRequest.builder()
//                    .destination(Destination.builder()
//                            .toAddresses(toEmail)
//                            .build())
//                    .content(EmailContent.builder()
//                            .template(Template.builder()
//                                    .templateName("VerificationCodeTemplate3")
//                                    .templateData(templateDataJson)
//                                    .build())
//                            .build())
//                    .fromEmailAddress(fromEmail)
//                    .build();

            // 이메일 전송
            SendEmailResponse response = sesV2Client.sendEmail(emailRequest);
            System.out.println("Email sent! Message ID: " + response.messageId());
        } catch (Exception e) {
            System.err.println("Error sending template email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
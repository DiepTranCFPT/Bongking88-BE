package com.example.demo.service;



import com.example.demo.model.EmailDetail;
import com.example.demo.respository.AuthenticationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    AuthenticationRepository authenticationRepository;

    public void sendMailTemplate(EmailDetail emailDetail) {
        try {
            Context context = new Context();
            context.setVariable("username", emailDetail.getName());
            context.setVariable("buttonValue", emailDetail.getButtonValue());
            context.setVariable("link", emailDetail.getLink());
            context.setVariable("email", emailDetail.getRecipient());
//            context.setVariable("registrationDate",java.time.Clock.systemUTC().instant());


            String text = templateEngine.process("emailtemplate", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom("swpproject2024@gmail.com");
            mimeMessageHelper.setTo(emailDetail.getRecipient());
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setSubject(emailDetail.getSubject());

            if (emailDetail.getAttachment() != null) {
                mimeMessageHelper.addAttachment(emailDetail.getAttachment().getFilename(), emailDetail.getAttachment());
            }

            javaMailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
    }
    public void sendMailTemplateOwner(EmailDetail emailDetail) {
        try {
            Context context = new Context();
            context.setVariable("username", emailDetail.getName());
            context.setVariable("buttonValue", emailDetail.getButtonValue());
            context.setVariable("link", emailDetail.getLink());
            context.setVariable("email", emailDetail.getRecipient());
//            context.setVariable("registrationDate",java.time.Clock.systemUTC().instant());


            String text = templateEngine.process("emailtemolateowner", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom("swpproject2024@gmail.com");
            mimeMessageHelper.setTo(emailDetail.getRecipient());
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setSubject(emailDetail.getSubject());

            if (emailDetail.getAttachment() != null) {
                mimeMessageHelper.addAttachment(emailDetail.getAttachment().getFilename(), emailDetail.getAttachment());
            }
            javaMailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
    }
    public void sendMailTemplateForgot(EmailDetail emailDetail) {
        try {
            // Log recipient email address for debugging
            System.out.println("Sending email to: " + emailDetail.getRecipient());

            // Validate email address
            if (emailDetail.getRecipient() == null || emailDetail.getRecipient().isEmpty()) {
                throw new IllegalArgumentException("Recipient email address is invalid or empty");
            }

            Context context = new Context();
            context.setVariable("username", emailDetail.getName());
            context.setVariable("buttonValue", emailDetail.getButtonValue());
            context.setVariable("link", emailDetail.getLink());
            context.setVariable("email", emailDetail.getRecipient());
            context.setVariable("registrationDate", java.time.Clock.systemUTC().instant());

            String text = templateEngine.process("forgotpasswordemailtemplate", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom("swpproject2024@gmail.com");
            mimeMessageHelper.setTo(emailDetail.getRecipient());
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setSubject(emailDetail.getSubject());

            if (emailDetail.getAttachment() != null) {
                mimeMessageHelper.addAttachment(emailDetail.getAttachment().getFilename(), emailDetail.getAttachment());
            }

            javaMailSender.send(mimeMessage);

            // Log success
            System.out.println("Email successfully sent to: " + emailDetail.getRecipient());

        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}



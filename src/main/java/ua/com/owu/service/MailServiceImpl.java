package ua.com.owu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ua.com.owu.models.Manager;
import ua.com.owu.models.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Component
public class MailServiceImpl implements MailService {
    @Autowired
    JavaMailSender mailSender;

    @Override
    public void sendSimpleMessage(String email, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setText(text);
        messageHelper.setSubject(subject);
        messageHelper.setTo(email);
        mailSender.send(message);
    }

    @Override
    public void sendConfirmMessage(String email, User user) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setText("<h2>Nice to meet you "+ user.getFirstName()+"!</h2><hr><a style=\"display:block, background:green, height:100px\" href=\"http://localhost:8080/user/confirm/2255542"+user.getId()+"\">Please confirm your email!</a>", true);
        messageHelper.setSubject("Confirm your email!");
        messageHelper.setTo(email);
        mailSender.send(message);
    }



    @Override
    public void sendMessageWithAttachment(String email, String subject, String text, MultipartFile file) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setText(text);
        messageHelper.setSubject(subject);
        messageHelper.addAttachment( file.getOriginalFilename() ,file);
        messageHelper.setTo(email);
        mailSender.send(message);
    }


}

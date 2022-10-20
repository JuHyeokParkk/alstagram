package sns.alstagram.mail;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("my@email.com");

        msg.setSubject("test");
        msg.setText("<h1>hi</h1>");


        javaMailSender.send(msg);
    }

    public void sendEmailWithAttachment(long userId, String email, String uuid) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(email);

        helper.setSubject("sign up mail");

        String url = "http://localhost:8080/email-auth/" + userId + "/" + uuid;

        String linkHtml =  "<a href=\"" + url +"\">click here</a>";

        helper.setText(linkHtml, true);

        javaMailSender.send(msg);
    }
}

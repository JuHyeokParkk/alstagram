package sns.alstagram.mail;

import lombok.RequiredArgsConstructor;
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

    public void sendEmailWithAttachment() throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo("my@email.com");

        helper.setSubject("subject~");

        helper.setText("<h1>hello</h1>\n" +
                "<a href=\"https://www.youtube.com/\">link</a>" +
                "<img src=\"img-url\"", true);

        javaMailSender.send(msg);
    }
}

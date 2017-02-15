package main.mail;

import main.model.Order;
import main.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service("mailManager")
public class MailManager {
    private JavaMailSenderImpl mailSender;

    @Autowired
    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void notifyUserAboutCompletedOrder(User user, Order order) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;

        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Your order is completed!");
            messageHelper.setText("<html>\n" +
                            "<body>\n" +
                            "<h3>Hello, " + user.getName() + "!</h3>\n" +
                            "\n" +
                            "<div>\n" +
                            "    Your order is completed. Check it out: <a href=\"http://127.0.0.1:8080/order/done\">http://127.0.0.1:8080/order/done</a>\n" +
                            "</div>\n" +
                            "\n" +
                            "</body>\n" +
                            "</html>",
                    true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println(e.toString());
            return;
        }
    }
}
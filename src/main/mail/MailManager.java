package main.mail;

import main.dao.SettingDAO;
import main.dao.impl.SettingDAOImpl;
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
import java.util.Arrays;

@Service("mailManager")
public class MailManager {
    private JavaMailSenderImpl mailSender;

    @Autowired
    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void notifySystemAboutNewOrder(Order order) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;
        SettingDAO settingDAO = new SettingDAOImpl();

        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom(mailSender.getUsername());
            messageHelper.setTo(settingDAO.getServiceEmails());
            messageHelper.setSubject("New order is received!");
            messageHelper.setText("<html>\n" +
                            "<body>\n" +
                            "<h3>Hello!</h3>\n" +
                            "\n" +
                            "<div>\n" +
                            "    New order is received. Check it out: <a href=\"http://test.gmotravel.ru:8080/order/undefined\">http://test.gmotravel.ru:8080/order/undefined</a>\n" +
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

    public void notifyUserAboutCompletedOrder(User user, Order order) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;

        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom(mailSender.getUsername());
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Your order is completed!");
            messageHelper.setText("<html>\n" +
                            "<body>\n" +
                            "<h3>Hello, " + user.getName() + "!</h3>\n" +
                            "\n" +
                            "<div>\n" +
                            "    Your order is completed. Check it out: <a href=\"http://test.gmotravel.ru:8080/order/done\">http://test.gmotravel.ru:8080/order/done</a>\n" +
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
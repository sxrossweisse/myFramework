package com.base.myFramework.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.myFramework.dto.MessageAttr;
import com.base.myFramework.enums.error.ErrorCodeEnum;
import com.base.myFramework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author suxin
 * @Package com.yololiv.utils
 * @ClassName EmailUtil
 * @date 2020/4/14 下午2:28
 * <p>
 */
@Slf4j
@Component
public class EmailUtil {

    @Value("${email.protocol}")
    private String protocol;
    @Value("${email.host}")
    private String host;
    @Value("${email.port}")
    private Integer port;
    @Value("${email.auth}")
    private Boolean auth;
    @Value("${email.ssl_enable}")
    private Boolean sslEnable;
    @Value("${email.debug}")
    private Boolean debug;
    @Value("${email.send_addr}")
    private String sendAddr;
    @Value("${email.send_addr_pwd}")
    private String sendAddrPwd;
    @Value("${email.timeout}")
    private String timeout;
    @Value("${email.connection_timeout}")
    private String connectionTimeout;
    @Value("${email.write_timeout}")
    private String writeTimeout;

    private static final String VALIDATE_EMAIL = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";

    private Session session;
    private Transport transport;

    /**
     * 校验邮箱
     *
     * @param email 邮箱地址
     * @return  是否有效
     */
    public boolean verification(String email) {
        if (!StringUtils.hasText(email)) {
            throw new BusinessException(ErrorCodeEnum.NO_DATA);
        }
        Pattern pattern = Pattern.compile(VALIDATE_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void sendMailCore(String mailTitle, String mailContent, List<String> recEmailAddr) throws MessagingException {
        MessageAttr messageAttr = new MessageAttr();
        messageAttr.setMailTitle(mailTitle);
        messageAttr.setMailContent(mailContent);
        messageAttr.setRecEmailAddr(recEmailAddr);
        sendMailCore(messageAttr);
    }

    public void sendMailCore(MessageAttr m) throws MessagingException {
        Message message = createMessage(m);
        Transport transport = createTransport();
        if (!transport.isConnected()) {
            transport.connect(this.sendAddr, this.sendAddrPwd);
        }
        log.debug("MessageAttr: {}", JSON.toJSONString(m));
        transport.sendMessage(message, message.getAllRecipients());
        checkTransport();
    }

    private Session createSession() {
        Properties properties = new Properties();
        // 连接协议
        properties.put("mail.transport.protocol", protocol);
        // 主机名
        properties.put("mail.smtp.host", host);
        // 端口号
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", auth);
        // 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.smtp.ssl.enable", sslEnable);
        // 设置是否显示debug信息 true 会在控制台显示相关信息
        properties.put("mail.debug", debug);
        properties.put("mail.smtp.timeout", timeout);
        properties.put("mail.smtp.connectiontimeout", connectionTimeout);
        properties.put("mail.smtp.writetimeout", writeTimeout);
        return Session.getInstance(properties);
    }

    private Message createMessage(MessageAttr messageAttr) throws MessagingException {
        Message message = new MimeMessage(this.session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress(this.sendAddr));
        // 设置收件人邮箱地址
        List<InternetAddress> internetAddresses = new ArrayList<>(messageAttr.getRecEmailAddr().size());
        for (int i = 0; i < messageAttr.getRecEmailAddr().size(); i++) {
            internetAddresses.add(new InternetAddress(messageAttr.getRecEmailAddr().get(i)));
        }
        message.setRecipients(Message.RecipientType.TO, internetAddresses.toArray(new InternetAddress[]{}));
//        message.setRecipient(Message.RecipientType.TO, new InternetAddress("xxx@qq.com"));//一个收件人
        // 设置邮件标题
        message.setSubject(messageAttr.getMailTitle());
        // 设置邮件内容
        message.setContent(messageAttr.getMailContent(), "text/html; charset=utf-8");
        return message;
    }

    private Transport createTransport() throws NoSuchProviderException {
        if (null == session) {
            this.session = createSession();
        }
        if (null == transport) {
            this.transport = session.getTransport();
        }
        return transport;
    }

    private void checkTransport() throws MessagingException {
        if (null != this.transport) {
            transport.close();
        }
    }
}

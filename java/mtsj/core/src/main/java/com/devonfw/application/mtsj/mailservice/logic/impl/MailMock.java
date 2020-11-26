package com.devonfw.application.mtsj.mailservice.logic.impl;

import com.devonfw.application.mtsj.mailservice.logic.api.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;

/**
 * Mock class for Mail Service. The mail content is printed as a Log
 */
@Named
@ConditionalOnProperty(prefix = "spring.mail", name = "enabled", havingValue = "false")
public class MailMock implements Mail {
    @Value("${mailservice.url:http://host.docker.internal:8088}")
    private String mailserviceUrl;

    /**
     * Logger instance.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MailMock.class);

    @Override
    public boolean sendMail(String to, String subject, String text) {

        StringBuilder sb = new StringBuilder();
        sb.append("To: ").append(to).append("|").append("Subject: ").append(subject).append("|").append("Text: ")
                .append(text);
        LOG.info(sb.toString());

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<MailInfo> mailInfoHttpEntity = new HttpEntity<>(new MailInfo(to, subject, text), headers);
            LOG.info(">>>> Send Confirmation to Mailservice Url: " + mailserviceUrl);
            ResponseEntity<Void> responseEntity = restTemplate.postForEntity(mailserviceUrl + "/mail",
                    mailInfoHttpEntity, Void.class);
            LOG.info(">>>> Mailservice returned successful with code" + responseEntity.getStatusCodeValue());
        } catch (HttpClientErrorException | HttpServerErrorException exc) {
            LOG.warn("Mail sending not successful with exception from mail service", exc);
            throw exc;
        } catch (Exception exc) {
            LOG.warn("Mail sending not successful but ignored (this is only a mock)", exc);
        }

        return true;
    }

    private static class MailInfo {
        private String recipient;
        private String subject;
        private String text;

        public MailInfo(String recipient, String subject, String text) {
            this.recipient = recipient;
            this.subject = subject;
            this.text = text;
        }

        public String getRecipient() {
            return recipient;
        }

        public String getSubject() {
            return subject;
        }

        public String getText() {
            return text;
        }
    }
}

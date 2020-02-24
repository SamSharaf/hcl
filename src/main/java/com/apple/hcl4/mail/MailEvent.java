package com.apple.hcl4.mail;

import com.apple.hcl4.domain.User;

public class MailEvent {

    private final User user;
    private final String subjectTemplate;
    private final String contentTemplate;

    public MailEvent(User user, String subjectTemplate, String contentTemplate) {
        this.user = user;
        this.subjectTemplate = subjectTemplate;
        this.contentTemplate = contentTemplate;
    }

    public User getUser() {
        return user;
    }

    public String getSubjectTemplate() {
        return subjectTemplate;
    }

    public String getContentTemplate() {
        return contentTemplate;
    }

}

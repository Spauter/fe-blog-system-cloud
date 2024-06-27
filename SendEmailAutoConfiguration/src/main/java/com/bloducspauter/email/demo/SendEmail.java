package com.bloducspauter.email.demo;

import java.io.IOException;

public interface SendEmail {
    void sendEmailVerifyCode(String request, String to, String code) throws IOException;

    void sendAuditNotice(String request,String to,boolean isAudited)throws IOException;
}

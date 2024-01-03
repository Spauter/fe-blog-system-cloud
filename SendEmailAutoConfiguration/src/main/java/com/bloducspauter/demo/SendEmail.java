package com.bloducspauter.demo;

import java.io.IOException;

public interface SendEmail {
    void sendEmailVerifyCode(String request, String to, String code) throws IOException;

    void sendAuditNotice(String request,String to,String isAudited)throws IOException;
}

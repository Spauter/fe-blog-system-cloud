package com.bs.controller;

import com.bs.service.EmailServiceImpl;
import com.bs.util.IsValudEmailAddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class AuditNoticeController {
    @Autowired
    private EmailServiceImpl emailService;

    @Value("${spring.mail.username}")
    private String from;//发件人地址

    private static final String title="博客稿件审核通知";

    private static String getText(boolean isAudited) {
        String text;
        if(isAudited){
            text="亲爱的作者\n" +
                    "\n" +
                    "非常感谢您向我们投稿您的博客文章。经过我们的审核，您的稿件已经通过，并将在我们的平台上发表。\n" +
                    "\n" +
                    "我们很高兴能与您分享这个好消息，并期待您更多的优秀作品。如果您有任何问题或需要进一步的支持，请随时与我们联系。\n" +
                    "\n" +
                    "再次感谢您的贡献，祝您一切顺利！\n" +
                    "\n" +
                    "\t\t最好的祝愿，\n" +
                    "\t\t博客审核团队";
        }else {
            text="亲爱的作者\n" +
                    "\n" +
                    "非常感谢您向我们投稿您的博客文章。我们非常抱歉地通知您，经过审核，您的稿件未能通过。\n" +
                    "\n" +
                    "在做出此决定时，我们考虑了多种因素，包括但不限于内容质量、是否符合我们的标准、是否符合我们的目标受众的需求等。我们希望您理解，这并不意味着您的文章没有价值或您没有才华，只是它可能不适合我们平台。\n" +
                    "\n" +
                    "如果您需要更多关于您的稿件的具体反馈或建议，我们很乐意提供。请随时与我们联系，我们可以帮助您改进您的作品，以便未来更好地满足我们的标准。\n" +
                    "\n" +
                    "再次感谢您的投稿和理解。我们希望在未来还能看到您的作品。\n" +
                    "\n" +
                    "\t\t祝好！\n" +
                    "\t\t博客审核团队";
        }
        return text;
    }

    @RequestMapping("notice")
    public Map<String,Object>sendAuditNotice(HttpServletRequest request){
        Map<String,Object>map=new HashMap<>();
        String email=request.getParameter("email");
        if (email == null) {
            map.put("code", 500);
            map.put("msg", "Empty email address");
            log.warn("Empty email address");
            return map;
        }
        if (new IsValudEmailAddressUtil().isValidEmail(email)) {
            log.warn("Invalid email address!");
            map.put("code", 500);
            map.put("msg", "Invalid email address");
            return map;
        }
        String audit=request.getParameter("isAudited");
        boolean isAudited= Boolean.parseBoolean(audit);
        String text = getText(isAudited);
        try {
            log.info("from:"+from);
            log.info("to:"+email);
            emailService.sendEmail(from,email,title,text);
            map.put("code",200);
            map.put("msg","邮件发送成功");
            log.info("Email send successfully");
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", "发送邮件失败，原因:" + e.getCause());
            log.error("Failed to send email");
        }
        return map;
    }


}

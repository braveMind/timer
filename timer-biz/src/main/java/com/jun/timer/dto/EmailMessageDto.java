package com.jun.timer.dto;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

/**
 * @author jun
 * @Date 17/11/6 .
 * @des:
 */
public class EmailMessageDto implements Serializable {

    /*sendType 远程文件发送 */
    private String sendType;
    private InputStream[] file;
    private String[] fileName;

    public String[] getFileName() {
        return fileName;
    }

    public void setFileName(String[] fileName) {
        this.fileName = fileName;
    }

    public InputStream[] getFile() {
        return file;
    }

    public void setFile(InputStream[] file) {
        this.file = file;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    private static final long serialVersionUID = 1L;
    /*接收人*/
    private String receiver;
    /*抄送人*/
    private String cc;
    /*邮件类型*/
    private String emailType;
    /*业务数据*/
    private Map<String, Object> bizMap;

    /*关联业务主键ID*/
    private Long refId;
    /*邮件ID*/
    private Long emailMsgId;
    /*email附件*/
    private String attachement;
    /*email 主题*/
    private String title;
    /*邮件内容*/
    private String content;
    /*附件名称*/
    private String attachementName;

    public String getAttachementName() {
        return attachementName;
    }

    public void setAttachementName(String attachementName) {
        this.attachementName = attachementName;
    }

    public String getAttachement() {
        return attachement;
    }

    public void setAttachement(String attachement) {
        this.attachement = attachement;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public EmailMessageDto(String receiver, String cc, String emailType, Long refId,
                           Map<String, Object> bizMap) {
        super();
        this.receiver = receiver;
        this.emailType = emailType;
        this.bizMap = bizMap;
        this.refId = refId;
    }


    public EmailMessageDto(String title, String receiver, Long refId, String content) {
        this.title = title;
        this.receiver = receiver;
        this.content = content;
        this.refId = refId;
    }


    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }


    public Long getEmailMsgId() {
        return emailMsgId;
    }

    public void setEmailMsgId(Long emailMsgId) {
        this.emailMsgId = emailMsgId;
    }

    public Map<String, Object> getBizMap() {
        return bizMap;
    }

    public void setBizMap(Map<String, Object> bizMap) {
        this.bizMap = bizMap;
    }

    @Override
    public String toString() {
        return "EmailMsgDto [receiver=" + receiver + ", cc=" + cc
                + ", emailType=" + emailType + ", bizMap=" + bizMap + "]";
    }

}




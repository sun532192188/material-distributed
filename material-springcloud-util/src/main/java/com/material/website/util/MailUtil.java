/*package com.material.website.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;


*//**
 * 发送邮件工具类
 * @author Sunxiaorong
 *
 *//*
public class MailUtil {
	
	private static MailUtil mailUtil;
	
	private static String propertites = "javamail.properties";
	
	public static MailUtil newInstance(){
		if(mailUtil == null)mailUtil = new MailUtil();
		return mailUtil;
	}
	
    *//**
     * 发送普通邮件
     * @param subject  邮件主题
     * @param message  邮件内容
     * @param toUserList  发送用户
     *//*
    public  void sendTextMail(String subject,String message,Map<String,String>toUserList){
		try{
			SimpleEmail email = new SimpleEmail();
			email.setHostName(getSettingInfo("mail.host"));
			for(String str : toUserList.keySet()){
				email.addTo(str,toUserList.get(str));
			}
			email.setFrom(getSettingInfo("mail.username"),subject);
			email.setSubject(subject);
			email.setCharset("utf-8");
			email.setMsg(message);
			email.setAuthentication(getSettingInfo("mail.username"),getSettingInfo("mail.password"));
			email.send();
			System.out.println("邮件发送成功...");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
    
    
	*//**
	 * 发送html 邮件
	 * @param subject
	 * @param message
	 * @param toUserList
	 * @param attachementList 附件信息
	 *//*
	public static void sendHtmlMail(String subject,String message,Map<String,String>toUserList,List<String>attachementList){
		try {
			HtmlEmail email = new HtmlEmail ();
			//附件，可以定义多个附件对象
			EmailAttachment attachment = null;
			//设置附件信息
			if(attachementList != null){
				for(String attachement :attachementList){
					attachment = new EmailAttachment();
					attachment.setPath(attachement);
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
					attachment.setDescription("");
					//添加附件
					email.attach(attachment);
				}
			}
			email.setCharset("utf-8");
			email.setHostName(getSettingInfo("mail.host"));
			String username = getSettingInfo("mail.username");
			String pwd = getSettingInfo("mail.password");
			email.setAuthentication(username,pwd);
			email.setFrom(username,subject);
			email.setSubject(subject);
			email.setHtmlMsg(message);
			for(String str : toUserList.keySet()){
				email.addTo(str,toUserList.get(str));
			}
			email.send();
			System.out.println("发送成功!");
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
    
    *//**
     * 获取配置文件属性值
     * @param key
     * @return
     *//*
    public static String getSettingInfo(String key){
    	//PropertiesUtil.newInstance().loadValue(key, propertites);
    	PropertiesUtil.newInstance().loadValue(key, propertites);
    	//System.out.println(str);
    	return str;
    }
	
}
*/
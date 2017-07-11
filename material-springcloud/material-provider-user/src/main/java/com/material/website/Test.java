package com.material.website;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.material.website.entity.User;

public class Test {
   
	public static void main(String[] args) {
		beanToXML();
	}
	
    public static void beanToXML() {  
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setAge(Short.valueOf("20"));
        user.setUsername("wangmazi");
        
        try {  
            JAXBContext context = JAXBContext.newInstance(User.class);  
            Marshaller marshaller = context.createMarshaller();  
            marshaller.marshal(user, System.out);  
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }  
  
    }  
	      
}

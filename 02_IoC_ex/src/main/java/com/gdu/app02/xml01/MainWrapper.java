package com.gdu.app02.xml01;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainWrapper {

  public static void main(String[] args) {
    
    AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml01/appCtx.xml");
    
    Contact contact = ctx.getBean("contact", Contact.class);
    Address address = ctx.getBean("address", Address.class);
    Person person = ctx.getBean("person", Person.class);
    
    System.out.println(contact.getMobile() + ", " + contact.getFax());
    System.out.println(address.getPostCode() + ", " + address.getJibun() + ", " + address.getRoad());
    System.out.println(person.getName() + ", " + person.getAddress() + ", " + person.getContact());

    ctx.close();
  }

}

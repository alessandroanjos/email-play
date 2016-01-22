package controllers;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import models.Usuario;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.*;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
 public static Result email(){
		
		ObjectNode result = Json.newObject();
		
		/*
		Email email = new Simple();
		email.setSubject("Simple email");
		email.setFrom("Primo Nerd <sandro.adsc@gmail.com>");
		email.addTo("Alessandro Anjos <alessandro_anjos@yahoo.com.br>");
		// adds attachment
		//email.addAttachment("attachment.pdf", new File("/some/path/attachment.pdf"));
		// adds inline attachment from byte array
		//email.addAttachment("data.txt", "data".getBytes(), "text/plain", "Simple data", EmailAttachment.INLINE);
		// sends text, HTML or both...
		email.setBodyText("A text message");
		email.setBodyHtml("<html><body><p>An <b>html</b> message</p></body></html>");
		MailerPlugin.send(email);*/
		
		// Load SMTP configuration
        String smtpHost = Play.application().configuration().getString( "smtp.host" );
        Integer smtpPort = Play.application().configuration().getInt( "smtp.port" );
        String smtpUser = Play.application().configuration().getString( "smtp.user" );
        String smtpPassword = Play.application().configuration().getString( "smtp.password");
        
        //Add emails em CC
        Collection<InternetAddress> emails = new ArrayList<InternetAddress>();
        InternetAddress intAdd = null;
		try {
			intAdd = new InternetAddress("sandro.adsc@gmail.com");
		} catch (AddressException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        emails.add(intAdd);

        Email email = new SimpleEmail();
		try {
			email.setHostName(smtpHost);
			email.setSmtpPort(smtpPort);
			email.setAuthenticator(new DefaultAuthenticator(smtpUser, smtpPassword));
			email.setSSLOnConnect(true);
			email.setCc(emails);
			email.setFrom("sandro.adsc@gmail.com");
			email.setSubject("TestMail");
			email.addTo("alessandro_anjos@yahoo.com.br");
			email.setMsg("This is a test mail ... :-)");
			email.send();
			result.put("status", true);
			result.put("message", " Cadastrado com sucesso ");
			System.out.println("Enviado...");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			result.put("status", false);
			result.put("message", " Falha ao enviar e-mail. ");
			e.printStackTrace();
		}
		
		return ok(result);
	}

	public static Result addUsuario() {
		Usuario sm = new Usuario();
		sm.isAdministrador = true;
		sm.nome = "João";
		sm.email = "team@admin.com.br";
		sm.last_ip = obterIp();
		sm.nacionalidade = "Brésilien";
		sm.pais = "Brésil";
		sm.idade = "19";
		Usuario.create(sm);
		return ok("Record is added");
		
	}
	
	public static String obterIp(){
		InetAddress ia;
		String ip = null;
		String nome = null;
		Enumeration nis = null;
        try {
            nis = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (nis.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) nis.nextElement();
            Enumeration ias = ni.getInetAddresses();
            while (ias.hasMoreElements()) {
                ia = (InetAddress) ias.nextElement();
                if (!ia.getHostAddress().contains(":") && !(ia.getHostAddress().length()<9)) {//Nesse if está a charada, sendo que eu sei que meu ip começa com 10.132, por exemplo
                	ip=ia.getHostAddress();    
                }
                System.out.println("Teste " + ia.getHostAddress());
                if (!ni.getName().equals("lo")) {
                    System.out.println("test = "+ia.getHostAddress());
                }
            }
        }
        System.out.println("Nome: " + nome);
        System.out.println("IP: " + ip);
        
        return ip;
	}

}

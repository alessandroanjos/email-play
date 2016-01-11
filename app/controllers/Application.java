package controllers;

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

        Email email = new SimpleEmail();
		try {
			email.setHostName(smtpHost);
			email.setSmtpPort(smtpPort);
			email.setAuthenticator(new DefaultAuthenticator(smtpUser, smtpPassword));
			email.setSSLOnConnect(true);
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

}

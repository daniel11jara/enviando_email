package enviando_email.enviando_email;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	
	private String userName = "danieltijara@gmail.com";
	private String senha = "Ny9WSBSn";
	
	
	@Test
   public void testeEmail() {
		
		
		try {
			
			//instanciando a classe properties
			Properties properties = new Properties();
			
			//passando a autenticação / autorização
			properties.put("mail.smtp.auth", "true");
			
			//parte de segurança - tls
			properties.put("mail.smtp.starttls", "true");
			
			//servidor gmail do google
			properties.put("mail.smtp.host", "smtp.gmail.com");
			
			//passando a porta do servidor
			properties.put("mail.smtp.port", "465");
			
			//especifica a porta a ser conectada pelo socket
			properties.put("mail.smtp.socketFactory.port", "465");
			
			//classe socket de conexão ao smtp
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			
			//objeto que vai fazer a conexão
			Session session  = Session.getInstance(properties, new Authenticator() {
				
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					
					//passando o email e a senha
					return new PasswordAuthentication(userName, senha);
				}
			});
			
			System.out.println(senha);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
   }
}

package enviando_email.enviando_email;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	
	private String userName = "danieltijara@gmail.com";
	private String senha = "wydnwltdlfbqnhqp";
	
	
	@Test
   public void testeEmail() throws Exception {
		
		ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail("danieltijara@gmail.com", "daniel", "testando email", "esse e o texto do email");
		
		enviaEmail.EnviarEmai();
		
		
   }
}

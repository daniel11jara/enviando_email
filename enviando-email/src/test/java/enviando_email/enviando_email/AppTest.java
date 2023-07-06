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
		
		StringBuilder stringBuilderTextoEmail = new StringBuilder();
		
		stringBuilderTextoEmail.append("Ola, <br/><br/>");
		stringBuilderTextoEmail.append("Voce esta recebendo o acesso ao curso java <br/><br/>");
		stringBuilderTextoEmail.append("Para ter acesso clique no botao abaixo <br/><br/>");
		
		stringBuilderTextoEmail.append("<a target=\"_blank\" href=\"http://projetojavaweb.com/certificado-aluno/login\" style=\"color:#2525a7; padding: 14px 25px; text-align: center; text-decoration: none; display:inline-block; border-radius: 30px; font-size: 20px; font-family:courier; border: 3px solid green; background-color:#99DA39;\">Acessar Portal do aluno</a>");
		                              
		ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail("danieltijara@gmail.com", "daniel", "testando email", stringBuilderTextoEmail.toString());
		
		enviaEmail.EnviarEmaiAnexo(true);
		
		
   }
}

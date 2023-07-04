package enviando_email.enviando_email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ObjetoEnviaEmail {

	private String userName = "danieltijara@gmail.com";
	private String senha = "wydnwltdlfbqnhqp";
	
	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";
	
	// cor marron é o parâmetro
	// cor azul é o da classe
	public ObjetoEnviaEmail(String listaDestinatarios, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatarios;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}

	public void EnviarEmai() throws Exception {
		
			
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
			
			//classe que envia para o destinatário usando um array[] para várias pessoas
			Address[] toUser = InternetAddress.parse(listaDestinatarios);
			
			//classe de mensagens passando o objeto de conexão
			Message message = new MimeMessage(session);
			
			//quem está enviando? o userName que é o email criado
			message.setFrom(new InternetAddress(userName, nomeRemetente));
			
			//pra quem está enviando? email de destino -- no caso a lista de emails acima
			message.setRecipients(Message.RecipientType.TO, toUser);
			
			//assunto do email
			message.setSubject(assuntoEmail);
			
			//corpo do email
			message.setText(textoEmail);
			
			//enviando a mensagem
			Transport.send(message);
	}

}


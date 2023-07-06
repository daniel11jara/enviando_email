package enviando_email.enviando_email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

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

	public void EnviarEmai(boolean envioHtml) throws Exception {
		
			
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
			
			if (envioHtml) {
				message.setContent(textoEmail, "text/html; charset=utf-8");
			}else {
				//corpo do email
				message.setText(textoEmail);
			}
			
			
			
			//enviando a mensagem
			Transport.send(message);
	}
	
	
	public void EnviarEmaiAnexo(boolean envioHtml) throws Exception {
		
		
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
		
		//parte 1 do email que e o texto e descricao do email
		MimeBodyPart corpoEmail = new MimeBodyPart();
		
		
		if (envioHtml) {
			corpoEmail.setContent(textoEmail, "text/html; charset=utf-8");
		}else {
			//corpo do email
			corpoEmail.setText(textoEmail);
		}
		
		//parte 2 que sao os anexos
		MimeBodyPart anexoEmail = new MimeBodyPart();
		anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(simuladorDePdf(), "application/pdf")));
		anexoEmail.setFileName("anexoemail.pdf");
		
		//juntando as duas partes feitas acima
		Multipart multipart = new MimeMultipart();
		
		//adicionando no corpo do email
		multipart.addBodyPart(corpoEmail);
		
		multipart.addBodyPart(anexoEmail);
		
		message.setContent(multipart);
		
		
		//enviando a mensagem
		Transport.send(message);
}
	
	
	
	
	
	//aula 09
	private FileInputStream simuladorDePdf() throws Exception {
		
		//criando o documento
		Document document = new Document();
		
		//criando o arquivo
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		
		//escrevendo o pdf    no documento         esse file/arquivo
		PdfWriter.getInstance(document, new FileOutputStream(file));
		
		//abrindo o documento
		document.open();
		
		//escrevendo um paragrafo
		document.add(new Paragraph("Conteudo do pdf anexo com javamail"));
		
		//fechando o documento
		document.close();
		
		//retorna um pdf em branco com o texto do paragrafo
		return new FileInputStream(file);
		
	}

}


package enviando_email.enviando_email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
		
		//mandando varios anexos
		List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
		
		//colocando 4 anexos
		arquivos.add(simuladorDePdf());
		arquivos.add(simuladorDePdf());
		arquivos.add(simuladorDePdf());
		arquivos.add(simuladorDePdf());
		
		//colocando o corpo do email antes do for
		//juntando as duas partes feitas acima
		Multipart multipart = new MimeMultipart();
				
		//adicionando no corpo do email
		multipart.addBodyPart(corpoEmail);
		
		//fazendo um for para criacao dos arquivos 
		
		int index = 0;//criando a numeracao dos pdfs -- 1, 2, 3....
		for (FileInputStream fileInputStream : arquivos) {
			
		//parte 2 que sao os anexos
		//criando o anexo	
		MimeBodyPart anexoEmail = new MimeBodyPart();
		anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
		
		//colocando um nome para cada arquivo
		anexoEmail.setFileName("anexoemail"+ index +".pdf");//inserindo a numeracao
		
		//adicionando no objeto
		multipart.addBodyPart(anexoEmail);
		
		index++;
		
		}
		
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


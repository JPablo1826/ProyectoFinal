package co.edu.uniquindio.poo.utils;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import co.edu.uniquindio.poo.model.Factura;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class Correo {

	public static void main(String[] args) throws AddressException, MessagingException, Exception {
		enviarCorreoRegistro("emilibermudez6@gmail.com", "Emili","1122323");
	}

	public static void sendEmail(String toAddress, String subject, MimeBodyPart... messageBodyParts)
			throws AddressException, MessagingException, IOException {

		final String username = "unieventosuq@gmail.com";
		final String password = "efzu bxqv jhwr epfe";

		Message msg = new MimeMessage(getCreateSession(username, password));

		msg.setFrom(new InternetAddress(username));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		Multipart multipart = new MimeMultipart();
		for (MimeBodyPart messageBodyPart : messageBodyParts) {
			multipart.addBodyPart(messageBodyPart);
		}

		msg.setContent(multipart);

		Transport.send(msg, username, password);
	}

	public static void enviarCorreoCupon(String mail, String nombre, String codigo, String tipoCupon) throws Exception{
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(generarMensajeCupon(nombre, tipoCupon, codigo).toString(), "text/html");
		sendEmail(mail, "Redime tu Cupón | Unieventos", messageBodyPart);
	}
	public static void enviarCorreoRegistro(String mail, String nombre, String codigo) throws Exception{
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(generarMensajeRegistro(nombre, codigo).toString(), "text/html");
		sendEmail(mail, "verifica tu cuenta", messageBodyPart);
	}
	private static Object generarMensajeRegistro(String nombre, String codigo) {
		StringBuilder sbMsg1Solicitud = new StringBuilder();
		sbMsg1Solicitud.append("<p>");
		sbMsg1Solicitud.append("Estimado/a, ");
		sbMsg1Solicitud.append("<b>");
		sbMsg1Solicitud.append(nombre);
		sbMsg1Solicitud.append("</b>");
		sbMsg1Solicitud.append("<br><br>");
		sbMsg1Solicitud.append("Este es tu codigo para activar tu cuenta ");
		sbMsg1Solicitud.append(codigo);
		sbMsg1Solicitud.append("<br><br>");
		sbMsg1Solicitud.append("Para activar tu cuenta pega el siguiente código que ves en pantalla");
		sbMsg1Solicitud.append(generarMensajeCodigo(codigo));
		sbMsg1Solicitud.append("</p>");
		return sbMsg1Solicitud;
	}

	private static StringBuilder generarMensajeCupon(String nombre, String tipoCupon, String codigo) {
		StringBuilder sbMsg1Solicitud = new StringBuilder();
		sbMsg1Solicitud.append("<p>");
		sbMsg1Solicitud.append("Estimado/a, ");
		sbMsg1Solicitud.append("<b>");
		sbMsg1Solicitud.append(nombre);
		sbMsg1Solicitud.append("</b>");
		sbMsg1Solicitud.append("<br><br>");
		sbMsg1Solicitud.append("¡Te queremos tanto que te vamos a obsequiar un bono del ");
		sbMsg1Solicitud.append(tipoCupon);
		sbMsg1Solicitud.append("de descuento!");
		sbMsg1Solicitud.append("<br><br>");
		sbMsg1Solicitud.append("Para reclamarlo pega el siguiente código que ves en pantalla");
		sbMsg1Solicitud.append(generarMensajeCodigo(codigo));
		sbMsg1Solicitud.append("</p>");
		return sbMsg1Solicitud;
	}

	private static Session getCreateSession(String mailFrom, String mailPassword) {
		Session session = Session.getInstance(getGmailProperties(), new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailFrom, mailPassword);
			}
		});
		return session;
	}

	private static StringBuilder generarMensajeCodigo(String codigo) {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<center>");
		mensaje.append("	<table>");
		mensaje.append("		<tr>");
		generarTablaCodigo(mensaje, codigo);
		mensaje.append("		</tr>");
		mensaje.append("	</table>");
		mensaje.append("</center>");
		return mensaje;
	}

	private static void generarTablaCodigo(StringBuilder stringBuilder, String codigo) {
		for (int i = 0; i < 6; i++) {
			char caracter = codigo.charAt(i);
			stringBuilder.append("<td style=\"width: 30px; height: 30px; border-style: solid; border-width: 1px	;\">");
			stringBuilder.append("  <p style=\"text-align: center;\">");
			stringBuilder.append(Character.toString(caracter));
			stringBuilder.append("  </p>");
			stringBuilder.append("</td>");
		}
	}

	private static Properties getGmailProperties() {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		return prop;
	}
	public static void enviarCorreoFactura( Factura factura) throws Exception{
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(generarMensajeFactura(factura).toString(), "text/html");
		sendEmail(factura.getCompra().getCliente().getCorreo(),"Detalles de su factura", messageBodyPart);
	}
	private static Object generarMensajeFactura(Factura factura) {
		StringBuilder sbMsg1Solicitud = new StringBuilder();
		sbMsg1Solicitud.append("<p>");
		sbMsg1Solicitud.append("Estimado/a, ");
		sbMsg1Solicitud.append("<b>");
		sbMsg1Solicitud.append("</b>");
		sbMsg1Solicitud.append("<br><br>");
		sbMsg1Solicitud.append("Esta es la factura de la compra realizada");
		sbMsg1Solicitud.append(factura);
		sbMsg1Solicitud.append("<br><br>");
		sbMsg1Solicitud.append("Gracias por confiar en unieventos");
		sbMsg1Solicitud.append("</p>");
		return sbMsg1Solicitud;
	}
}
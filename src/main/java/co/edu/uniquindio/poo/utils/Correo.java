package co.edu.uniquindio.poo.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import co.edu.uniquindio.poo.model.*;
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
import java.util.ArrayList;

public class Correo {

	public static void main(String[] args) throws AddressException, MessagingException, Exception {

		Evento evento = Evento.builder()
				.IdEvento("IdEvento")
				.NombreEvento("NombreEvento")
				.ciudad("Ciudad")
				.Descripcion("Descripcion")
				.tipoEvento(TipoEvento.TEATRO)
				.imagen("imagen.jpg")
				.fecha(LocalDate.now())
				.Direccion("Direccion")
				.localidadGeneral(
						Localidad.builder().precio(100.00).tipo(TipoLocalidad.GENERAL).capacidad(500).build())
				.localidadVIP(Localidad.builder().precio(150.00).tipo(TipoLocalidad.VIP).capacidad(100).build())
				.build();

		// Creación de un objeto Cliente con Builder
		Cliente cliente = Cliente.builder()
				.ID("0132824")
				.nombre("Sarita")
				.telefono("3123456")
				.correo("saritalondonop@gmail.com")
				.contasena("1234")
				.build();
		Cliente cliente2 = Cliente.builder()
				.ID("0132824")
				.nombre("Amadour")
				.telefono("3123456")
				.correo("arroa03@gmail.com")
				.contasena("1234")
				.build();

		// Creación de un objeto Compra con Builder
		Compra compra = Compra.builder()
				.idCompra("idCompra")
				.cliente(cliente)
				.evento(evento)
				.cantidad(1)
				.localidad(TipoLocalidad.VIP) // Asumiendo que queremos la localidad VIP del evento
				.build();

		// Creación de una Factura con Builder
		Factura factura = Factura.builder()
				.compra(compra)
				.total(100.00)
				.fechaCompra(LocalDate.now())
				.codigoFactura("codigoFactura")
				.build();
				ArrayList<Cliente> clientes = new ArrayList<Cliente>();
				clientes.add(cliente);
				clientes.add(cliente2);
				enviarCorreoNuevoEvento(clientes, evento);

		// enviarCorreoFactura(factura);

	}

	public static void sendEmail(String[] toAddress, String subject, MimeBodyPart... messageBodyParts)
			throws AddressException, MessagingException, IOException {

		final String username = "unieventosuq@gmail.com";
		final String password = "efzu bxqv jhwr epfe";

		Message msg = new MimeMessage(getCreateSession(username, password));

		msg.setFrom(new InternetAddress(username));
		InternetAddress[] toAddresses = new InternetAddress[toAddress.length];
		for (int i = 0; i < toAddresses.length; i++) {
			toAddresses[i] = new InternetAddress(toAddress[i]);
		}
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

	public static void enviarCorreoCupon(String mail, String nombre, String codigo, String tipoCupon) throws Exception {
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(generarMensajeCupon(nombre, tipoCupon, codigo).toString(), "text/html");
		sendEmail(new String[] { mail }, "Redime tu Cupón | Unieventos", messageBodyPart);
	}

	public static void enviarCorreoRegistro(String mail, String nombre, String codigo) throws Exception {
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(generarMensajeRegistro(nombre, codigo).toString(), "text/html");
		sendEmail(new String[] { mail }, "verifica tu cuenta", messageBodyPart);
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

	public static void enviarCorreoFactura(Factura factura) throws Exception {
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(generarMensajeFactura(factura).toString(), "text/html");
		MimeBodyPart attachmentPart = new MimeBodyPart();
		File attachment = new File(generarQr(factura));
		attachmentPart.attachFile(attachment);
		sendEmail(new String[] { factura.getCompra().getCliente().getCorreo() }, "Detalles de su factura",
				messageBodyPart,
				attachmentPart);
	}

	private static String generarQr(Factura factura) throws Exception {
		String formato = "BEGIN:VEVENT\nSUMMARY:%s\nDTSTART;VALUE=DATE:%s\nDTEND;VALUE=DATE:%s\nLOCATION:%s\nDESCRIPTION:Codigo %s\\nPrecio %s\\nCantidad %s\\nFecha de Compra %s\nEND:VEVENT";
		String data = String.format(formato,
				factura.getCompra().getEvento().getNombreEvento(),
				factura.getCompra().getEvento().getFecha().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
				factura.getCompra().getEvento().getFecha().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
				factura.getCompra().getLocalidad().getNombre(),
				factura.getCompra().getIdCompra(),
				String.format("%.2f", factura.getTotal()),
				factura.getCompra().getCantidad() + "",
				factura.getCompra().getEvento().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		String path = System.getProperty("user.dir") + "/imagen.jpg";

		BitMatrix matrix = new MultiFormatWriter()
				.encode(data, BarcodeFormat.QR_CODE, 500, 500);
		Path pathNio = Paths.get(path);
		MatrixToImageWriter.writeToPath(matrix, "jpg", pathNio);
		return path;
	}

	private static String generarQrEvento(Evento evento) throws Exception {
		String formato = "BEGIN:VEVENT\nSUMMARY:%s\nDTSTART;VALUE=DATE:%s\nDTEND;VALUE=DATE:%s\nLOCATION:%s\nDESCRIPTION:Descripción %s\\nID %s\\nTipo de Evento %s\\nDirección %s\\nPrecio VIP %s\\nPrecio General %s\nEND:VEVENT";
		String data = String.format(formato,
				evento.getNombreEvento(),
				evento.getFecha().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
				evento.getFecha().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
				evento.getCiudad(),
				evento.getDescripcion(),
                evento.getIdEvento(),
				evento.getTipoEvento().toString(),
				evento.getDireccion(),
                String.format("%.2f", evento.getLocalidadVIP().getPrecio()),
                String.format("%.2f", evento.getLocalidadGeneral().getPrecio()));

		String path = System.getProperty("user.dir") + "/imagen.jpg";

		BitMatrix matrix = new MultiFormatWriter()
				.encode(data, BarcodeFormat.QR_CODE, 500, 500);
		Path pathNio = Paths.get(path);
		MatrixToImageWriter.writeToPath(matrix, "jpg", pathNio);
		return path;
	}

	private static Object generarMensajeFactura(Factura factura) {
		StringBuilder sbMsg1Solicitud = new StringBuilder();
		sbMsg1Solicitud.append("<p>");
		sbMsg1Solicitud.append("Estimado/a, ");
		sbMsg1Solicitud.append("<b>");
		sbMsg1Solicitud.append("</b>");
		sbMsg1Solicitud.append("<br><br>");
		sbMsg1Solicitud.append(
				"Esta es la factura de la compra realizada , por favor escanee el codigo Qr adjunto para verla");
		sbMsg1Solicitud.append("</p>");

		return sbMsg1Solicitud;
	}

	public static void enviarCorreoNuevoEvento(List<Cliente> listaClientes, Evento evento) throws Exception {
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(generarMensajeNuevoEvento(listaClientes, evento).toString(), "text/html");
		MimeBodyPart attachmentPart = new MimeBodyPart();
		File attachment = new File(generarQrEvento(evento));
		attachmentPart.attachFile(attachment);
		int size = listaClientes.size();
		String mails[] = new String[size];
		for (int i = 0; i < size; i++) {
            mails[i] = listaClientes.get(i).getCorreo();
        }
		sendEmail(mails, "Detalles del nuevo evento", messageBodyPart,
				attachmentPart);
	}

	private static StringBuilder generarMensajeNuevoEvento(List<Cliente> listaClientes, Evento evento) {
		StringBuilder sbMsg1Solicitud = new StringBuilder();
		sbMsg1Solicitud.append("<p>");
		sbMsg1Solicitud.append("Estimado/a, ");
		sbMsg1Solicitud.append("<b>");
		sbMsg1Solicitud.append("</b>");
		sbMsg1Solicitud.append("<br><br>");
		sbMsg1Solicitud.append(
				"Estamos felices de que hagas parte de este nuevo evento que tenemos para ti, te va a encantar no esperes mas. Compra ya tu boleta!");
		sbMsg1Solicitud.append("</p>");

		return sbMsg1Solicitud;
	}
}

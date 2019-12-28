package com.challenge.moeda.main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base32;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * classe inicial que gera o QRCode que deve ser adicionado ao APP Google Authenticator
 * @author filipe.garbim
 *
 */
public class QRCode {

	private static final String FILE_PATH = "/Users/filipe.garbim/eclipse-workspace/google-auth-challenge/barcode.png";
	private static final String COMPANY_NAME = "Travel Xchange";
	private static final String EMAIL = "garbim.filipe@gmail.com";

	public static void main(String[] args) throws WriterException, IOException {

		String secretKey = generateSecretKey();
		String email = EMAIL;
		String companyName = COMPANY_NAME;
		String barCodeUrl = getGoogleAuthenticatorBarCode(secretKey, email, companyName);
		System.out.println(barCodeUrl);

		createQRCode(barCodeUrl, FILE_PATH, 300, 300);
	}

	/**
	 * gera a String de dados do bar code do Google
	 * 
	 * @param secretKey - secret key gerada anteriormente
	 * @param account   - email do usuário
	 * @param issuer    - nome da empresa ou organização utilizado para
	 *                  identificação
	 * @return String de dados do bar code
	 */
	private static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
		try {
			return "otpauth://totp/" + URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
					+ "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20") + "&issuer="
					+ URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * cria a imagem do QR Code em um diretório específico
	 * 
	 * @param barCodeData - string de dados do bar code
	 * @param filePath    - diretório onde será gerado o QRCode
	 * @param height      - altura do QRCode
	 * @param width       - comprimento do QRCode
	 * @throws WriterException
	 * @throws IOException
	 */
	private static void createQRCode(String barCodeData, String filePath, int height, int width)
			throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE, width, height);
		try (FileOutputStream out = new FileOutputStream(filePath)) {
			MatrixToImageWriter.writeToStream(matrix, "png", out);
		}
	}

	/**
	 * gera a secretKey para o QRCode
	 * 
	 * @return string base32 da secretKey
	 */
	private static String generateSecretKey() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		Base32 base32 = new Base32();
		String base32Str = base32.encodeToString(bytes);
		System.out.println("SECRET KEY: " + base32Str);
		return base32Str;
	}

}

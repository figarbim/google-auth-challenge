package com.challenge.google;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import de.taimos.totp.TOTP;

public class TOTPConfig {

	/**
	 * validar o código TOTP gerado no APP Google Authenticator
	 * @param code - código gerado no Google Authenticator
	 * @return um booleano indicando se o código está válido
	 */
	public static boolean validateTOTPCode(String code, String secretKey) {

		boolean isCodeOK = Boolean.FALSE;

		if (code.equals(getTOTPCode(secretKey))) {
			System.out.println("Login realizado com sucesso!");
			isCodeOK = Boolean.TRUE;
		} else {
			System.out.println("Código 2FA inválido.");
		}

		return isCodeOK;
	}

	/**
	 * gerar o código TOTP
	 * @param secretKey - chave secret gerado anteriormente e cadastrado no APP Google Authenticator
	 * @return o código TOTP
	 */
	public static String getTOTPCode(String secretKey) {

		Base32 base32 = new Base32();
		byte[] bytes = base32.decode(secretKey);
		String hexKey = Hex.encodeHexString(bytes);
		return TOTP.getOTP(hexKey);
	}
	
}

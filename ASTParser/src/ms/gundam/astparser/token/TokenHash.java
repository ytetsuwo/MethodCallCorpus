package ms.gundam.astparser.token;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;



public class TokenHash {
	public static String calcHash(List<Token> tokens) {
		MessageDigest md = null;
		StringBuffer string = new StringBuffer();
		StringBuffer hashvalue = new StringBuffer();

		for (Token token: tokens) {
			string.append(token.dump());
		}
		
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		md.reset();
		byte[] hash = md.digest(string.toString().getBytes());
		
		for(Byte b : hash) {
			hashvalue.append(Integer.toHexString((b>>4) & 0x0F));
			hashvalue.append(Integer.toHexString(b & 0x0F));
		}

		return hashvalue.toString();
	}
}

package com.exam.portal.Controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.security.crypto.password.PasswordEncoder;



public class Encrypt implements PasswordEncoder {
   @Override
    public String encode(CharSequence rawPassword) {
        // Implement your password encoding logic here
        return sha256(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // Implement your logic to check if the rawPassword matches the encodedPassword
        return encodedPassword.equals(encode(rawPassword));
    }
	
	 public String sha256(String input) {
	        try {
	            MessageDigest digest = MessageDigest.getInstance("SHA-256");
	            byte[] hash = digest.digest(input.getBytes());

	            // Convert the byte array to a hexadecimal string
	            StringBuilder hexString = new StringBuilder();
	            for (byte b : hash) {
	                String hex = Integer.toHexString(0xff & b);
	                if (hex.length() == 1) {
	                    hexString.append('0');
	                }
	                hexString.append(hex);
	            }

	            return hexString.toString();
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException("Error encoding password", e);
	        }
	    }
}

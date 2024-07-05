package com.sftp.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;

import javax.crypto.SecretKey;

import org.springframework.messaging.MessagingException;


public class CommanUtils {
	
	public static void verifyChecksum(File originalFile, File decryptedFile) {
	    try {
	        String originalFileMd5 = generateMd5(originalFile);
	        System.out.println("Original file MD5 checksum : "+originalFileMd5);

	        String decryptedFileMd5 = generateMd5(decryptedFile);
	        System.out.println("Uploded file MD5 checksum : "+decryptedFileMd5);

	        if (originalFileMd5.equals(decryptedFileMd5)) {
	            System.out.println("Checksum verified successfully : " + originalFile.getName());
	        } else {
	            System.out.println("Checksum verification failed : " + originalFile.getName());
	        }
	    } catch (Exception e) {
	        System.out.println("Checksum verification exception : " + originalFile.getName());
	        e.printStackTrace();
	    }
	}

	public static File decryptUploadedFile(String encryptedData, File uploadDirFile, SecretKey secretKey,File originalFile) throws Exception {
	    byte[] decryptedDataBytes = EncryptionUtils.decrypt(encryptedData, secretKey);
	    Path decryptedFilePath = new File(uploadDirFile, "decrypted-" + originalFile.getName()).toPath();
	    Files.write(decryptedFilePath, decryptedDataBytes);
	    System.out.println("File decrypted and verified: " + decryptedFilePath.toString());
	    return decryptedFilePath.toFile();
	}
	

	public static void moveFile(File fileToMove, String localDirectory,String currentArchiveDirectory) {
		try {
			File archiveDir = new File(currentArchiveDirectory);
			if (!archiveDir.exists()) {
				archiveDir.mkdirs();
			}

			// Create subdirectory
			File sourceSubDir = new File(archiveDir, localDirectory);
			if (!sourceSubDir.exists()) {
				sourceSubDir.mkdirs();
			}

			// movie file to subdirectory within archive directory
			Path sourcePath = fileToMove.toPath();
			Path targetPath = new File(sourceSubDir, fileToMove.getName()).toPath();
			Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("File moved to archive directory: " + targetPath);

		} catch (IOException e) {
			fileNotTransferred(fileToMove, e);
			throw new MessagingException("Failed to move and verify file: " + fileToMove.getName(), e);
		}
	}

	public static String generateMd5(File file) {
		byte[] md5Bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] fileBytes = Files.readAllBytes(file.toPath());
			md5Bytes = md.digest(fileBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		for (byte b : md5Bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	private static void fileNotTransferred(File file, Exception e) {
		System.out.println("Failed to transfer file: " + file.getName());
		System.out.println("Reason: " + e.getMessage());
	}


}

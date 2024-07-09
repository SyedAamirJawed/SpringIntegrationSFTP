package com.sftp.config;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.sftp.util.CommanUtils;
import com.sftp.util.EncryptionUtils;

@Configuration
@EnableIntegration
@EnableScheduling
public class SpringIntegrationConfig {
	
	
/*
	//this class transfer file local folder just for testing.. it can read more than one folder at a time
	 
	private volatile File originalFile;
	private volatile String currentUploadDirectory;
	private volatile String currentArchiveDirectory;
	SecretKey secretKey;
	
	@Value("${dircetry.parent}")
	private String parent;
	
	@Value("${dircetry.parent.child1}")
	private String child1;
	
	@Value("${dircetry.parent.child2}")
	private String child2;
	
	@Value("${dircetry.parent.child3}")
	private String child3;
	
	@Value("${dircetry.parent.child4}")
	private String child4;
	
	
	

	@Bean
	public ThreadPoolTaskScheduler taskScheduler() {
		return new ThreadPoolTaskScheduler();
	}

	@Bean
	public MessageChannel fileInputChannel() {
		return new DirectChannel();
	}

	@Bean
	@InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
	public FileReadingMessageSource fileReadingMessageSource1() {
		FileReadingMessageSource reader = new FileReadingMessageSource();
		reader.setDirectory(new File(parent+child1));
		return reader;
	}

	@Bean
	@InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
	public FileReadingMessageSource fileReadingMessageSource2() {
		FileReadingMessageSource reader = new FileReadingMessageSource();
		reader.setDirectory(new File(parent+child2));
		return reader;
	}

	@Bean
	@InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
	public FileReadingMessageSource fileReadingMessageSource3() {
		FileReadingMessageSource reader = new FileReadingMessageSource();
		reader.setDirectory(new File(parent+child3));
		return reader;
	}

	@Bean
	@InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
	public FileReadingMessageSource fileReadingMessageSource4() {
		FileReadingMessageSource reader = new FileReadingMessageSource();
		reader.setDirectory(new File(parent+child4));
		System.out.println("local dir" + reader);
		return reader;
	}

	@Bean
	@ServiceActivator(inputChannel = "fileInputChannel")
	public FileWritingMessageHandler fileWritingMessageHandler() throws NoSuchAlgorithmException {

	    secretKey = EncryptionUtils.generateKey();
	    System.out.println("your secretKey is :::" + secretKey);

	    updateCurrentUploadDirectory();
	    FileWritingMessageHandler writer = new FileWritingMessageHandler(new File(currentUploadDirectory));
	    writer.setAutoCreateDirectory(true);
	    writer.setExpectReply(false);
	    writer.setFileNameGenerator(dirFile -> {
	        originalFile = (File) dirFile.getPayload();
	        System.out.println("Transferring file: " + originalFile.getName());

	       
	        //create and update upload directory
	        String localDirectory = originalFile.getParentFile().getName();
	        String uploadDirectory = currentUploadDirectory + File.separator + localDirectory;
	        File uploadDirFile = new File(uploadDirectory);
	        if (!uploadDirFile.exists()) {
	            uploadDirFile.mkdirs();
	        }

	        //encrypt and Copy file to upload directory
	        try {
	            Path sourcePath = originalFile.toPath();
	            byte[] fileBytes = Files.readAllBytes(sourcePath);
	            String encryptedData = EncryptionUtils.encrypt(fileBytes, secretKey);
	            Path encryptedFilePath = new File(uploadDirFile, originalFile.getName() + ".enc").toPath();
	            Files.write(encryptedFilePath, encryptedData.getBytes(StandardCharsets.UTF_8));

	            File decryptedFile = CommanUtils.decryptUploadedFile(encryptedData, uploadDirFile,secretKey,originalFile);// decrypt uploaded file And and go for the md5 creation
	            CommanUtils.verifyChecksum(originalFile, decryptedFile);
	            CommanUtils.moveFile(originalFile, localDirectory,currentArchiveDirectory);

	        } catch (Exception e) {
	    		System.out.println("Failed to transfer file: " + originalFile.getName()+" | Reason : "+e.getMessage());
	        }

	        return originalFile.getName();
	    });
	    return writer;
	}
	
	public void scheduleDirectoryUpdate() {
		taskScheduler().scheduleAtFixedRate(this::updateCurrentUploadDirectory, 24 * 60 * 60 * 1000);
	}
	
	private void updateCurrentUploadDirectory() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		currentUploadDirectory = "C:\\Users\\dell\\Desktop\\upload\\" + date;
		currentArchiveDirectory = "C:\\Users\\dell\\Desktop\\Test\\archive\\" + date;
	}
	
	*/
}

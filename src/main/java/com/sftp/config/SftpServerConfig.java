package com.sftp.config;

import java.io.File;
import org.apache.sshd.sftp.client.SftpClient.DirEntry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.remote.handler.FileTransferringMessageHandler;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableIntegration
public class SftpServerConfig {
/*
    @Bean
    public SessionFactory<DirEntry> sftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost("sftp.server.com");
        factory.setPort(22);
        factory.setUser("username");
        factory.setPassword("password");
        factory.
        factory.setAllowUnknownKeys(true);
        return factory;
    }

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "3000"))
    public FileReadingMessageSource fileReadingMessageSource() {
        FileReadingMessageSource reader = new FileReadingMessageSource();
        reader.setDirectory(new File("C:\\Users\\dell\\Desktop\\Test"));
        return reader;
    }

    @Bean
    @ServiceActivator(inputChannel = "fileInputChannel")
    public MessageHandler sftpMessageHandler() {
        SftpRemoteFileTemplate template = new SftpRemoteFileTemplate(sftpSessionFactory());
        FileTransferringMessageHandler<DirEntry> handler = new FileTransferringMessageHandler<>(template);
        handler.setRemoteDirectoryExpressionString("'upload/'");
        handler.setAutoCreateDirectory(true);
        return handler;
    }
  */  
}


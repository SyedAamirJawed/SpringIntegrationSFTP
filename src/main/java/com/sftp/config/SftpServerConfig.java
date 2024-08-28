package com.sftp.config;

import java.io.File;
import org.apache.sshd.sftp.client.SftpClient.DirEntry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.remote.handler.FileTransferringMessageHandler;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableIntegration
public class SftpServerConfig {

	
    @Bean
    public SessionFactory<DirEntry> sftpSessionFactory1() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost("localhost");
        factory.setPort(4444);
        factory.setUser("aamir");
        factory.setPassword("aamir123");
        factory.setAllowUnknownKeys(true);
        return factory;
    }

    @Bean
    public SessionFactory<DirEntry> sftpSessionFactory2() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost("localhost");
        factory.setPort(2222);
        factory.setUser("ajay");
        factory.setPassword("ajay123");
        factory.setAllowUnknownKeys(true);
        return factory;
    }

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
    public FileReadingMessageSource fileReadingMessageSource() {
        FileReadingMessageSource reader = new FileReadingMessageSource();
        reader.setDirectory(new File("C:\\Users\\dell\\Desktop\\Test\\lookup"));
        return reader;
    }

    @Bean
    public MessageChannel fileInputChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "fileInputChannel")
    public MessageHandler sftpMessageHandler1() {
        SftpRemoteFileTemplate template = new SftpRemoteFileTemplate(sftpSessionFactory1());
        FileTransferringMessageHandler<DirEntry> handler = new FileTransferringMessageHandler<>(template);
        handler.setRemoteDirectoryExpressionString("'/testing2'");
        handler.setAutoCreateDirectory(true);
        return handler;
    }

    @Bean
    @ServiceActivator(inputChannel = "fileInputChannel")
    public MessageHandler sftpMessageHandler2() {
        SftpRemoteFileTemplate template = new SftpRemoteFileTemplate(sftpSessionFactory2());
        FileTransferringMessageHandler<DirEntry> handler = new FileTransferringMessageHandler<>(template);
        handler.setRemoteDirectoryExpressionString("'/testingg'");
        handler.setAutoCreateDirectory(true);
        return handler;
    }
    
    
    
}

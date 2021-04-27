package com.ownblog.caelog.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class ossUtil{

    private String endpoint;

    private String keyid;

    private String keysecret;

    private String bucketname;

}
package com.ecarinfo.traffic.po;
import java.io.Serializable;

public class ProxyHost implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private String host;//ip
	private Integer port;//port
	private Integer valid = 0;//是否有效
	private Integer lastReplyTime;//响应时间

    public String getHost () {
        return host;
    }

    public void setHost (String host) {
        this.host = host;
    }

    public Integer getPort () {
        return port;
    }

    public void setPort (Integer port) {
        this.port = port;
    }

    public Integer getValid () {
        return valid;
    }

    public void setValid (Integer valid) {
        this.valid = valid;
    }

    public Integer getLastReplyTime () {
        return lastReplyTime;
    }

    public void setLastReplyTime (Integer lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }
}
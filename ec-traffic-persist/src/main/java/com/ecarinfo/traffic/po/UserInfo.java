package com.ecarinfo.traffic.po;
import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String displayName;
	private String loginName;
	private String password;
	private String email;
	private Boolean isAdmin = false;
	private Boolean isValid = true;
	private Date lastLoginTime;
	private Integer departmentId;
	private String opName;
	private Date ctime;
	private Date utime;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getDisplayName () {
        return displayName;
    }

    public void setDisplayName (String displayName) {
        this.displayName = displayName;
    }

    public String getLoginName () {
        return loginName;
    }

    public void setLoginName (String loginName) {
        this.loginName = loginName;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public Boolean getIsAdmin () {
        return isAdmin;
    }

    public void setIsAdmin (Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getIsValid () {
        return isValid;
    }

    public void setIsValid (Boolean isValid) {
        this.isValid = isValid;
    }

    public Date getLastLoginTime () {
        return lastLoginTime;
    }

    public void setLastLoginTime (Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getDepartmentId () {
        return departmentId;
    }

    public void setDepartmentId (Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getOpName () {
        return opName;
    }

    public void setOpName (String opName) {
        this.opName = opName;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime () {
        return utime;
    }

    public void setUtime (Date utime) {
        this.utime = utime;
    }
}
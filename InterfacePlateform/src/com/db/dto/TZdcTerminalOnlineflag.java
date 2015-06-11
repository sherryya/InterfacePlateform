package com.db.dto;

import java.util.Date;

public class TZdcTerminalOnlineflag {
	private String terminalImei;

	private String onlimneStatus;

	private Date updateDate;

	private Date crtDate;

	public String getTerminalImei() {
		return terminalImei;
	}

	public void setTerminalImei(String terminalImei) {
		this.terminalImei = terminalImei;
	}

	public String getOnlimneStatus() {
		return onlimneStatus;
	}

	public void setOnlimneStatus(String onlimneStatus) {
		this.onlimneStatus = onlimneStatus;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

}

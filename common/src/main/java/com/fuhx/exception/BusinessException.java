package com.fuhx.exception;

import lombok.Data;

/**
 * 全局系统业务异常(unchecked)
 * @author fuhx
 */
@Data
public class BusinessException extends Exception {

	private static final long serialVersionUID = -8058289372452353570L;

	/** 错误码 */
	private String code;

	public BusinessException() {
		super();
	}

	public BusinessException(Throwable e) {
		super(e);
	}

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public BusinessException(String msg, Throwable e) {
		super(msg, e);
	}

	@Override
	public String getMessage() {
		return "【错误码：" + code + ",描述：" + getMessage() + "】";
	}

}
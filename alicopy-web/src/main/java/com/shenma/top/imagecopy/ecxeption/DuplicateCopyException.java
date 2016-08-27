package com.shenma.top.imagecopy.ecxeption;

/**
 * 系统业务异常
 * @author luocanfeng
 * @date 2011-6-30 10:20:46
 */
public class DuplicateCopyException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 2332608236621015980L;

	private String code;

	public DuplicateCopyException() {
		super();
	}

	public DuplicateCopyException(String message) {
		super(message);
	}

	public DuplicateCopyException(String code, String message) {
		super(message);
		this.code = code;
	}

	public DuplicateCopyException(Throwable cause) {
		super(cause);
	}

	public DuplicateCopyException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateCopyException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}

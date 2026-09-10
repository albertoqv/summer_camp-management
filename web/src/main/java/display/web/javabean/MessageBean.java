package display.web.javabean;

public class MessageBean {
	private String url = "";
	private MessageType type;
	private String message = "";
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setError(String message) {
		this.type = MessageType.ERROR;
		this.message = message;
	}
	public void setSuccess(String message) {
		this.type = MessageType.SUCCESS;
		this.message = message;
	}
	public void setInfo(String message) {
		this.type = MessageType.INFO;
		this.message = message;
	}
}

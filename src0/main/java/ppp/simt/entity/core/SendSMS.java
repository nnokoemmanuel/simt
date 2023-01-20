package ppp.simt.entity.core;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name="outbox")
public class SendSMS implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int ID;

	@Column(name="SendingDateTime")
	private Date SendingDateTime;

	@Column(name="text")
	private String text;

	@Column(name="DestinationNumber")
	private String destinationNumber;

	@Column(name="creatorID")
	private String creator;

	@Column(name="UpdatedInDB")
	private Date updatedInDB;

	@Column(name="InsertIntoDB")
	private Date insertIntoDB;

	@Column(name="SendBefore")
	private Time sendBefore;

	@Column(name="SendAfter")
	private Time sendAfter;

	@Column(name="RelativeValidity")
	private int relativeValidity;

	@Column(name="SenderID")
	private String senderID;

	@Column(name="Retries")
	private String retries;

	@Column(name="Priority")
	private int priority;

	@Column(name="coding")
	private String coding;

	@Column(name="auth")
	private String auth;

	@Column(name="class")
	private int classe;

	@Column(name="TextDecoded")
	private String textDecoded;

	@Column(name="multipart")
	private String multipart;

	@Column(name="sendingTimeOut")
	private Date sendingTimeOut;

	@Column(name="UDH")
	private String udh;

	@Column(name="DeliveryReport")
	private String deliveryReport;

	public SendSMS() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return ID;
	}

	public void setId(int ID) {
		this.ID = ID;
	}

	public Date getSendingDateTime() {
		return SendingDateTime;
	}

	public void setSendingDateTime(Date sendingDateTime) {
		SendingDateTime = sendingDateTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDestinationNumber() {
		return destinationNumber;
	}

	public void setDestinationNumber(String destinationNumber) {
		this.destinationNumber = destinationNumber;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getUpdatedInDB() {
		return updatedInDB;
	}

	public void setUpdatedInDB(Date updatedInDB) {
		this.updatedInDB = updatedInDB;
	}

	public Date getInsertIntoDB() {
		return insertIntoDB;
	}

	public void setInsertIntoDB(Date insertIntoDB) {
		this.insertIntoDB = insertIntoDB;
	}

	public Time getSendBefore() {
		return sendBefore;
	}

	public void setSendBefore(Time sendBefore) {
		this.sendBefore = sendBefore;
	}

	public Time getSendAfter() {
		return sendAfter;
	}

	public void setSendAfter(Time sendAfter) {
		this.sendAfter = sendAfter;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public int getRelativeValidity() {
		return relativeValidity;
	}

	public void setRelativeValidity(int relativeValidity) {
		this.relativeValidity = relativeValidity;
	}

	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	public String getRetries() {
		return retries;
	}

	public void setRetries(String retries) {
		this.retries = retries;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getCoding() {
		return coding;
	}

	public void setCoding(String coding) {
		this.coding = coding;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public int getClasse() {
		return classe;
	}

	public void setClasse(int classe) {
		this.classe = classe;
	}

	public String getTextDecoded() {
		return textDecoded;
	}

	public void setTextDecoded(String textDecoded) {
		this.textDecoded = textDecoded;
	}

	public String getMultipart() {
		return multipart;
	}

	public void setMultipart(String multipart) {
		this.multipart = multipart;
	}

	public Date getSendingTimeOut() {
		return sendingTimeOut;
	}

	public void setSendingTimeOut(Date sendingTimeOut) {
		this.sendingTimeOut = sendingTimeOut;
	}

	public String getUdh() {
		return udh;
	}

	public void setUdh(String udh) {
		this.udh = udh;
	}

	public String getDeliveryReport() {
		return deliveryReport;
	}

	public void setDeliveryReport(String deliveryReport) {
		this.deliveryReport = deliveryReport;
	}

}
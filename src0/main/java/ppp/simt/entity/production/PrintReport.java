package ppp.simt.entity.production;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="print_report")
public class PrintReport {
	private static final long serialVersionUID = 1L;


	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id", updatable = false, nullable = false)
	 private int id;
	 
	 @Column(name="card_numbr")
	 private String cardNumber;
	 
	 @Column(name="status")
	 private String status;
	 
	 @Column(name="print_date")
	 private java.util.Date printDate;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "application_id")
	 private Application application;
	 
	 public PrintReport() {
			super();

		} 

	public PrintReport(String cardNumber, String status, Date printDate, Application application) {
		super();
		
		this.cardNumber = cardNumber;
		this.status = status;
		this.printDate = printDate;
		this.application = application;
	}
	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public java.util.Date getPrintDate() {
		return printDate;
	}

	public void setPrintDate(java.util.Date printDate) {
		this.printDate = printDate;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
	 
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	 
}

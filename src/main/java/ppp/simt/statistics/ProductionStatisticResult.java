package ppp.simt.statistics;

public class ProductionStatisticResult {

	private int officeId;
	private int capacityId;
	private Long total;
	
	public ProductionStatisticResult(int officeId, int capacityId, Long total) {
		this.officeId = officeId;
		this.capacityId = capacityId;
		this.total = total;
	}

	public int getOfficeId() {
		return officeId;
	}

	public void setOfficeId(int officeId) {
		this.officeId = officeId;
	}

	public int getCapacityId() {
		return capacityId;
	}

	public void setCapacityId(int capacityId) {
		this.capacityId = capacityId;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	
	
}

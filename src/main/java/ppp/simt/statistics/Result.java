package ppp.simt.statistics;

import ppp.simt.entity.core.Office;

public class Result {
	private Office office;
	private Long total;
	
	public Result(Office office, Long total) {
		this.office = office;
		this.total = total;
	}
	
	public Result(){
		
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	

}

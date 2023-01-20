package ppp.simt.statistics;

import java.util.List;

import ppp.simt.entity.core.Category;
import ppp.simt.entity.production.ProcessType;


public class ProductionResult {
	private ProcessType processType;
	private List<Result> result;
	
	public ProductionResult(ProcessType processType, List<Result> result){
		this.processType = processType;
		this.result = result;	
	}
	
	public ProductionResult(){
		
	}

	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}

	public List<Result> getResult() {
		return result;
	}

	public void setResult(List<Result> result) {
		this.result = result;
	}

	
	
	}

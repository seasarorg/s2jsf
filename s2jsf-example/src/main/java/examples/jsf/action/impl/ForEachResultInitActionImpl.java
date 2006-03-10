package examples.jsf.action.impl;

import java.util.List;

import examples.jsf.action.ForEachResultInitAction;
import examples.jsf.dto.ForEachDto;

public class ForEachResultInitActionImpl implements ForEachResultInitAction {

	private int index;

	private List forEachDtoList;
	
	private ForEachDto forEachDto;
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setForEachDtoList(List forEachDtoList) {
		this.forEachDtoList = forEachDtoList;
	}
	
	public ForEachDto getForEachDto() {
		return forEachDto;
	}

	public String initialize() {
		forEachDto = (ForEachDto) forEachDtoList.get(index);
		return null;
	}
	
}
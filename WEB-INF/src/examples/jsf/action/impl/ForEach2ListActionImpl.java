package examples.jsf.action.impl;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import examples.jsf.action.ForEach2ListAction;
import examples.jsf.dto.ForEach2Dto;

public class ForEach2ListActionImpl implements ForEach2ListAction {

	private List forEach2DtoList;
	
	public void setForEach2DtoList(List forEach2DtoList) {
		this.forEach2DtoList = forEach2DtoList;
	}
	
	public String update() {
		for (Iterator i = forEach2DtoList.iterator(); i.hasNext(); ) {
			ForEach2Dto dto = (ForEach2Dto) i.next();
			if (dto.isDelete()) {
				i.remove();
			}
		}
		return null;
	}

	public String addRow() {
		forEach2DtoList.add(new ForEach2Dto());
		return null;
	}
}
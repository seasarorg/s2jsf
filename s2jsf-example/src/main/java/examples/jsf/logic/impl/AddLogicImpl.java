package examples.jsf.logic.impl;

import examples.jsf.dto.AddDto;
import examples.jsf.logic.AddLogic;

public class AddLogicImpl implements AddLogic {

	public AddLogicImpl() {
	}

	public int calculate(AddDto addDto) {
		return addDto.getArg1() + addDto.getArg2();
	}

}

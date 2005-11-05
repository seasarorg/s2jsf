package examples.jsf.action.impl;

import examples.jsf.action.AddAction;
import examples.jsf.dto.AddDto;
import examples.jsf.logic.AddLogic;

public class AddActionImpl implements AddAction {

    private AddDto addDto;

    private AddLogic addLogic;

    public void setAddDto(AddDto addDto) {
        this.addDto = addDto;
    }

    public void setAddLogic(AddLogic addLogic) {
        this.addLogic = addLogic;
    }

    public String calculate() {
        int result = addLogic.calculate(addDto);
        addDto.setResult(result);
        return null;
    }
}
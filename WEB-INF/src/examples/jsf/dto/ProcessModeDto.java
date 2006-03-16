package examples.jsf.dto;

public class ProcessModeDto {

    public static final String COMPONENT = "instance = session";

    private int processMode;

    public int getProcessMode() {
        return processMode;
    }

    public void setProcessMode(int processMode) {
        this.processMode = processMode;
    }

}

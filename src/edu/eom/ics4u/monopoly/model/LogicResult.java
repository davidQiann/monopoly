package edu.eom.ics4u.monopoly.model;
//LogicResult.java: Model used for generating logic result by game logic

public class LogicResult {
    public static final int RESULT_SUCCESS=200;
    public static final int RESULT_FAIL=500;
    public static final int RESULT_UNAVAILABLE = 400;
    private int resultcode;
    private String message;
    private int value1;
    private int value2;


    public LogicResult(int resultcode, String message) {
        this.resultcode = resultcode;
        this.message = message;
    }

    public void printInfo() {
        System.out.println("Logic code" + resultcode + " Message:" + message);
    }
    /**
     * @return the value2
     */
    public int getValue2() {
        return value2;
    }

    /**
     * @param value2 the value2 to set
     */
    public void setValue2(int value2) {
        this.value2 = value2;
    }

    /**
     * @return the value1
     */
    public int getValue1() {
        return value1;
    }

    /**
     * @param value1 the value1 to set
     */
    public void setValue1(int value1) {
        this.value1 = value1;
    }

    /**
     * @return the resultcode
     */
    public int getResultcode() {
        return resultcode;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * @param resultcode the resultcode to set
     */
    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }
    
}

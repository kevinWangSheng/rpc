package com.kevin.domain;

/**
 * @author wang
 * @create 2023-2023-10-17:52
 */
public class EasyResult {
    private String msg;

    private boolean success;

    private String title;

    public EasyResult() {
    }

    public EasyResult(String msg, boolean success, String title) {
        this.msg = msg;
        this.success = success;
        this.title = title;
    }

    public static EasyResult buildSuccess(){
        EasyResult result = new EasyResult();
        result.setMsg("ok");
        result.setSuccess(true);
        return result;
    }



    public static EasyResult buildFail(String msg, String title){
        return new EasyResult(msg, false, title);
    }

    public static EasyResult buildFail(String msg){
        return new EasyResult(msg, false, "error");
    }

    public static EasyResult buildErrResult(Exception e){
        return new EasyResult(e.getMessage(), false, "error");
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

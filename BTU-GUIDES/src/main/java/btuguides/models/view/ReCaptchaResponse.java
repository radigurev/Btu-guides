package btuguides.models.view;

public class ReCaptchaResponse {
    private boolean success;
    private String challangeTs;
    private String hostName;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getChallangeTs() {
        return challangeTs;
    }

    public void setChallangeTs(String challangeTs) {
        this.challangeTs = challangeTs;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}

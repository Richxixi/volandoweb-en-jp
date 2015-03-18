package models;


public class objPayRes {

    private String status;

    private String lidm;
    private String last4DigitPAN;
    private String authAmt;
    private String authCode;
    private String numberOfPay;
    private String originalAmt;
    private String offsetAmt;
    private String errDesc;
    private String errcode;


    public String getStatus() {
        return this.status;
    }
    public String getLidm() {
        return this.lidm;
    }
    public String getLast4DigitPAN() {
        return this.last4DigitPAN;
    }
    public String getAuthAmt() {
        return this.authAmt;
    }
    public String getAuthCode() {
        return this.authCode;
    }
    public String getNumberOfPay() {
        return this.numberOfPay;
    }
    public String getOriginalAmt() {
        return this.originalAmt;
    }
    public String getOffsetAmt() {
        return this.offsetAmt;
    }
    public String getErrDesc() {
        return this.errDesc;
    }
    public String getErrcode() {
        return this.errcode;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setLidm(String lidm) {
        this.lidm = lidm;
    }
    public void setLast4DigitPAN(String last4DigitPAN) {
        this.last4DigitPAN = last4DigitPAN;
    }
    public void setAuthAmt(String authAmt) {
        this.authAmt = authAmt;
    }
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    public void setNumberOfPay(String numberOfPay) {
        this.numberOfPay = numberOfPay;
    }
//    public void setOriginalAmt(String originalAmt) {
//        this.originalAmt = originalAmt;
//    }
    public void setOffsetAmt(String offsetAmt) {
        this.offsetAmt = offsetAmt;
    }
    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }
    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }
}

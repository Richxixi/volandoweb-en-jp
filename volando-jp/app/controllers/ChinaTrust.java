package controllers;

import com.hyweb.posapi_npg.Decrypt;
import com.hyweb.posapi_npg.Encrypt;
import com.hyweb.posapi_npg.auth_in_mac_24;
import play.Play;

public class ChinaTrust {

    private final String authResUrl = Play.configuration.getProperty("payment.resUrl");

    private final String merId = Play.configuration.getProperty("payment.merId");
    private final String merchantId = Play.configuration.getProperty("payment.merchantId");
    private final String terminalId = Play.configuration.getProperty("payment.terminalId");
    private final String authUrl = Play.configuration.getProperty("payment.authUrl");
    private final String macKey = Play.configuration.getProperty("payment.macKey");

    private final String AUTH_STATUS_OK = "0";
    private final String AUTH_STATUS_HYPOS_ERROR = "9";
    private final String AUTH_STATUS_EZPOS_ERROR = "10";
    private final String AUTH_STATUS_HYPOS_ERROR_ST_MER_EXPDATE_EXPIRED = "ed";

    private final String TX_TYPE_REGULAR_DEAL = "0";
    private final String TX_TYPE_INSTALLMENT_DEAL = "1";
    private final String OPTION_FOR_REGULAR_DEAL = "1";
    private final String MERCHANT_NAME = "Volando Store";
    private final String AUTO_CAP_SWITCH = "0";// "0": tun off, "1": turn on

    private Decrypt decrypt;

    public String getUrlEnc(String orderId, Float amt) {
        // To generate Message Authentication Code
        String key = macKey;
        String merchantID = merchantId;
        String terminalID = terminalId;
        String lidm = orderId;
        String txType = TX_TYPE_REGULAR_DEAL;
        //String txType = TX_TYPE_INSTALLMENT_DEAL;// for test and future
        String option = null;
        if (txType == TX_TYPE_INSTALLMENT_DEAL) {
            option = "2";// number of payments
        } else {
            option = OPTION_FOR_REGULAR_DEAL;
        }
        String purchAmt = String.valueOf(Math.round(amt));
        auth_in_mac_24 aim = new auth_in_mac_24(merchantID, terminalID, lidm, purchAmt, txType, option, key);
        String inMac = aim.getMAC();

        // To generate encoded URL for the deal
        String merchantName = MERCHANT_NAME;
        String merID = merId;
        Encrypt enc= new Encrypt();
        enc.setMerchantID(merchantID);
        enc.setTerminalID(terminalID);
        enc.setMerchantName(merchantName);
        enc.setLidm(lidm);
        enc.setMerID(merID);
        enc.setCustomize("0");
        enc.setPurchAmt(purchAmt);
        enc.setTxType(TX_TYPE_REGULAR_DEAL);
        //enc.setTxType(TX_TYPE_INSTALLMENT_DEAL);// for test and future
        enc.setNumberOfPay("1");
        enc.setAutoCap(AUTO_CAP_SWITCH);
        enc.setInMac(inMac);
        enc.setAuthResURL(authResUrl);
        //enc.setOrderDesc(orderDesc);
        enc.Encryption(key);
        return enc.getEnc();
    }

    public void getDecrypt(String urlResEnc) {
        this.decrypt = new Decrypt();
        try {
            this.decrypt.Decryption(urlResEnc, macKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMerId(){
        return this.merId;
    }

    public String getAuthUrl() {
        return this.authUrl;
    }

    public String getAuthOk() {
        return this.AUTH_STATUS_OK;
    }

    public String getAuthHyposError() {
        return this.AUTH_STATUS_HYPOS_ERROR;
    }

    public String getAuthEzposError() {
        return this.AUTH_STATUS_EZPOS_ERROR;
    }

    public String getAuthHyposErrorStMerExpdateExpired() {
        return this.AUTH_STATUS_HYPOS_ERROR_ST_MER_EXPDATE_EXPIRED;
    }


    public String getResStatus() {
        return this.decrypt.getStatus();
    }

    public String getResLidm() {
        return this.decrypt.getLidm();
    }

    public String getResLast4DigitPAN() {
        return this.decrypt.getLast4digitPAN();
    }

    public String getResAuthAmt() {
        return this.decrypt.getAuthAmt();
    }

    public String getResAuthCode() {
        return this.decrypt.getAuthCode();
    }

    public String getResNumberOfPay() {
        return this.decrypt.getNumberOfPay();
    }

//    public String getResOriginalAmt() {
//        return this.decrypt.getOriginalAmt();
//    }

//    public String getResOffsetAmt() {
//        return this.decrypt.getOffsetAmt();
//    }

    public String getResErrDesc() {
        return this.decrypt.getErrDesc();
    }

    public String getResErrcode() {
        return this.decrypt.getErrcode();
    }
}

package controllers;

import models.objPayRes;
import play.Logger;
import play.Play;
import play.mvc.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Payment extends Controller{
    public static void beforeCheckOut(float price, String orderId) {
        price = 1000f;
        orderId = "000001";

        ChinaTrust chinaTrust = new ChinaTrust();
        String URLEnc = chinaTrust.getUrlEnc(orderId, price);
        String auth = chinaTrust.getAuthUrl();
        String merID = chinaTrust.getMerId();
        render(URLEnc, auth, merID);
    }
    /**
     * @param URLResEnc Encrypted payment results. Name of the parameter MUST be "URLResEnc".
     * */
    public static void afterCheckOut(String URLResEnc) {
        Logger.debug("Payment: Encrypted Response = %s ", URLResEnc);
        ChinaTrust chinaTrust = new ChinaTrust();
        chinaTrust.getDecrypt(URLResEnc);

        objPayRes payRes = getPayRes(chinaTrust);

        String status = payRes.getStatus();
        if (status == null) {
            return;
        }
        String errCode = null;
        String errDesc = null;
        if (status.equals(chinaTrust.getAuthOk())) {
            Logger.debug("Payment: AUTH_STATUS_OK");
            String orderId = payRes.getLidm();
            String last4 = payRes.getLast4DigitPAN();
            String authAmt = payRes.getAuthAmt();
            String authCode = payRes.getAuthCode();
//            String numberOfPay = payRes.getNumberOfPay();
//            String originalAmt = payRes.getOriginalAmt();
//            String offsetAmt = payRes.getOffsetAmt();

            render();

        } else {
            errDesc = payRes.getErrDesc();
            errCode = payRes.getErrcode();
            String orderID = payRes.getLidm();
            judgeExceptionCode(status, errCode, errDesc, orderID, chinaTrust);
        }
    }
    private static void judgeExceptionCode(String status, String errCode, String errDesc, String orderID, ChinaTrust chinaTrust) {
        StringBuilder uri = new StringBuilder();

        if (status.equals(chinaTrust.getAuthEzposError()) ||
                (status.equals(chinaTrust.getAuthHyposError()) && errCode.equals(chinaTrust.getAuthHyposErrorStMerExpdateExpired()))) {
            uri.append("/Payment/desc/");
            try {
                uri.append(URLEncoder.encode(errDesc, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                uri.append(errDesc);
            }
            cancelOrder(orderID);
        } else {
            uri.append("/Payment/code/");
            try {
                uri.append(URLEncoder.encode(errCode, "utf-8"));
                uri.append("?status=").append(URLEncoder.encode(status, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                uri.append(errCode);
                uri.append("?status=").append(status);
            }
        }
        redirect(uri.toString());
    }

    public static void showPaymentExceptionDesc(String errDesc) {
        render(errDesc);
    }

    public static void showPaymentExceptionCode(String status, String errCode) {

        render(status, errCode);
    }

    private static void cancelOrder(String orderID) {
//
    }

    private static objPayRes getPayRes(ChinaTrust chinaTrust){
        objPayRes res = new objPayRes();
        res.setStatus(chinaTrust.getResStatus());
        res.setLidm(chinaTrust.getResLidm());
        res.setLast4DigitPAN(chinaTrust.getResLast4DigitPAN());
        res.setAuthAmt(chinaTrust.getResAuthAmt());
        res.setAuthCode(chinaTrust.getResAuthCode());
        res.setNumberOfPay(chinaTrust.getResNumberOfPay());
//        res.setOriginalAmt(getResOriginalAmt.invoke(obj, null).toString());
//        res.setOffsetAmt(getResOffsetAmt.invoke(obj, null).toString());
        res.setErrDesc(chinaTrust.getResErrDesc());
        res.setErrcode(chinaTrust.getResErrcode());

        return res;
    }


}

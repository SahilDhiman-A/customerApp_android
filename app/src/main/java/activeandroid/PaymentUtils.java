package activeandroid;

import com.spectra.consumer.BuildConfig;

import org.apache.http.util.EncodingUtils;

public class PaymentUtils {

    public static final String URL = "https://epay.spectra.co/onlinepayment/getcustomerpaymentAPI.php";

    public static byte[] getPaymentParam(String payable_amount, String tds_value_amount, String canID, String mobile, String email) {

        String buildType = BuildConfig.BUILD_SEGMENT;
        String postData = "";
        try {
            if (tds_value_amount != null) {
                postData = "uid=cust_app&passcode=C!7$uT@99#&payamnt=" + payable_amount + "&session=" +
                        canID + System.currentTimeMillis() + "&returnurl=returnurl" + "&emailid=" + email + "&tds_amount=" + tds_value_amount + "&mobileno=" + mobile + "&" +
                        "can_id=" + canID + "&segment=Web&paytype=Normal&sub_segment=" + buildType;
            } else {
                postData = "uid=cust_app&passcode=C!7$uT@99#&payamnt=" + payable_amount + "&session=" +
                        canID + System.currentTimeMillis() + "&returnurl=returnurl" + "&emailid=" + email + "&mobileno=" + mobile + "&" +
                        "can_id=" + canID + "&segment=Web&paytype=Normal&sub_segment=" + buildType;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EncodingUtils.getBytes(postData, "base64");
    }
}

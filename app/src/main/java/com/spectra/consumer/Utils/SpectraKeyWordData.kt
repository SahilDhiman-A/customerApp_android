package com.spectra.consumer.Utils

import com.spectra.consumer.Models.KeyWord
import com.spectra.consumer.service.repository.ApiConstant.RESET_PASSWORD
import java.util.*
import kotlin.collections.ArrayList

internal object SpectraKeyWordData {

    data class KeyWordKey(val id: Int, val keyName: String);
    @JvmStatic
    val data: HashMap<KeyWordKey, KeyWord>
        get() {
            val keyWord = HashMap<KeyWordKey, KeyWord>()
            //View SR Status
            val keySearchSR = ArrayList<String>();


            keyWord.apply {
                val KeyWord_CreateSr = KeyWord(1, Constant.VIEW_SR_STATUS, Constant.VIEW_SR_STATUS, true, true)
                put(KeyWordKey(1, Constant.MY_SR), KeyWord_CreateSr)
                put(KeyWordKey(1, Constant.SR), KeyWord_CreateSr)
                put(KeyWordKey(1, Constant.RAISE_NEW_SR), KeyWord_CreateSr)
                put(KeyWordKey(1, Constant.COMPLAINT), KeyWord_CreateSr)
                put(KeyWordKey(1, Constant.ISSUE), KeyWord_CreateSr)
                put(KeyWordKey(1, Constant.SERVICE_REQUEST), KeyWord_CreateSr)
                put(KeyWordKey(1, Constant.RAISE_CONCERN), KeyWord_CreateSr)

                ///View Invoice List

                val KeyWord_GetInvoice = KeyWord(2, Constant.VIEW_INVOICE_LIST, Constant.VIEW_INVOICE_LIST, true, true)
                put(KeyWordKey(2, Constant.BILL), KeyWord_GetInvoice)
                put(KeyWordKey(2, Constant.INVOICE), KeyWord_GetInvoice)
                put(KeyWordKey(2, Constant.PAYMENT), KeyWord_GetInvoice)

                //Home Screen
                val KeyWord_HomeScreen = KeyWord(3, Constant.HOME, Constant.HOME, true, true)
                put(KeyWordKey(3, Constant.HOME), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.MY_ACCOUNT), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.ACCOUNT), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.MY_SR), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.RAISE_NEW_SR), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.SR), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.SERVICE_REQUEST), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.BILL), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.INVOICE), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.PAYMENT), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.DATA), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.DATA_CONSUMED), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.DATA_CHECK), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.DATA_VOLUME), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.MOBILE_NO), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.MY_ADDRESS), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.RESET_PASSWORD), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.MANAGE_CONTACT), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.ANOTHER_ACCOUNT), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.ANOTHER_ACCOUNT), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.RMN), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.REGISTERED_MOBILE_NO), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.CONTACT_DETAILS), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.CONTACT), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.ADDRESS), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.PASSWORD), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.PLAN_DETAILS), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.SPEED), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.NEW_PLAN), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.CUSTOMER_NAME), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.CAN_ID), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.PHONE_NO), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.EMAIL_ID), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.CHANGE), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.TOP_UP), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.NEW_TOP_UP), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.DATA_REQUIRED), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.TOP_UP_STATUS), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.ADD_ANOTHER_ACCOUNT), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.ADD_ANOTHER_CAN_ID), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.ATTACHED_ANOTHER_ACCOUNT), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.NEW_ACCOUNT), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.ADD_CAN_ID), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.PAY), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.AUTO_PAY), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.SET_UP), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.AUTO_DEBIT), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.SWITCH_ACCOUNT), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.MY_PROFILE), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.USAGES), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.VIEW_DATA), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.OPTION), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.BILL_CYCLE), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.CHECK_MY_BILL), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.CHANGE_PLAN), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.TARIFF), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.SELF_CARE_PORTAL), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.SELF_CARE), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.SELF_CARE), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.SHIFT_DIFFERENT_ROOM), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.SHIFT_WITHIN_THE_SOCIETY), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.TRANSFER_OWNERSHIP), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.TRANSFER), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.FUP), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.FTTH), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.FTTH), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.SYMMETRIC_SPEED), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.ASYMMETRIC_BANDWIDTH), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.IP_ADDRESS), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.STATIC), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.DYNAMIC), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.SERVICE_AVAILABILITY), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.GB_DATA), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.IDENTIFY_PROOF), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.WI_FI), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.ROUTER), KeyWord_HomeScreen)
                put(KeyWordKey(3, Constant.SPECTRA_VOICE), KeyWord_HomeScreen)

                //MY_ACCOUNT

                val KeyWord_MyAccount = KeyWord(4, Constant.MY_ACCOUNT, Constant.MY_ACCOUNT, true, true)
                put(KeyWordKey(4, Constant.ACCOUNT), KeyWord_MyAccount)
                put(KeyWordKey(4, Constant.MY_ACCOUNT), KeyWord_MyAccount)
                put(KeyWordKey(4, Constant.MOBILE_NO), KeyWord_MyAccount)
                put(KeyWordKey(4, Constant.MY_ADDRESS), KeyWord_MyAccount)
                put(KeyWordKey(4, Constant.RESET_PASSWORD), KeyWord_MyAccount)
                put(KeyWordKey(4, Constant.MANAGE_CONTACT), KeyWord_MyAccount)
                put(KeyWordKey(4, Constant.ANOTHER_ACCOUNT), KeyWord_MyAccount)
                put(KeyWordKey(4, Constant.SWITCH_ACCOUNT), KeyWord_MyAccount)

                //MY_PROFILE

                val KeyWord_MyProfile = KeyWord(5, Constant.MY_PROFILE, Constant.MY_PROFILE, true, true)
                put(KeyWordKey(5, Constant.MY_ACCOUNT), KeyWord_MyProfile)
                put(KeyWordKey(5, Constant.MOBILE_NO), KeyWord_MyProfile)
                put(KeyWordKey(5, Constant.MY_ADDRESS), KeyWord_MyProfile)
                put(KeyWordKey(5, Constant.RESET_PASSWORD), KeyWord_MyProfile)
                put(KeyWordKey(5, Constant.MANAGE_CONTACT), KeyWord_MyProfile)
                put(KeyWordKey(5, Constant.ANOTHER_ACCOUNT), KeyWord_MyProfile)
                put(KeyWordKey(5, Constant.SWITCH_ACCOUNT), KeyWord_MyProfile)

                //SWITCH_ACCOUNT
                val KeyWord_switch_acc = KeyWord(6, Constant.SWITCH_ACCOUNT, Constant.SWITCH_ACCOUNT, true, true)
                put(KeyWordKey(6, Constant.MENU_MY_ACCOUNT), KeyWord_switch_acc)

                //VIEW_USAGE
                val KeyWord_view_usage = KeyWord(7, Constant.VIEW_USAGE, Constant.VIEW_USAGE, true, true)
                put(KeyWordKey(7, Constant.DATA), KeyWord_view_usage)
                put(KeyWordKey(7, Constant.DATA_CONSUMED), KeyWord_view_usage)
                put(KeyWordKey(7, Constant.DATA_CHECK), KeyWord_view_usage)
                put(KeyWordKey(7, Constant.DATA_VOLUME), KeyWord_view_usage)
                put(KeyWordKey(7, Constant.DATA_USAGE), KeyWord_view_usage)
                put(KeyWordKey(7, Constant.UTILIZATION), KeyWord_view_usage)
                put(KeyWordKey(7, Constant.VIEW_USAGE), KeyWord_view_usage)
                put(KeyWordKey(7, Constant.BANDWIDTH_USAGE), KeyWord_view_usage)

                //MY_PLAN

//                    Plan details/ Data/ Speed/ Invoice/ Bill/Payment/New Plan/Upgrade

                val KeyWord_my_plan = KeyWord(8, Constant.MY_PLAN, Constant.MY_PLAN, true, true)
                put(KeyWordKey(8, Constant.PLAN_DETAILS), KeyWord_my_plan)
                put(KeyWordKey(8, Constant.DATA), KeyWord_my_plan)
                put(KeyWordKey(8, Constant.SPEED), KeyWord_my_plan)
                put(KeyWordKey(8, Constant.BILL), KeyWord_my_plan)
                put(KeyWordKey(8, Constant.INVOICE), KeyWord_my_plan)
                put(KeyWordKey(8, Constant.PAYMENT), KeyWord_my_plan)
                put(KeyWordKey(8, Constant.NEW_PLAN), KeyWord_my_plan)
                put(KeyWordKey(8, Constant.UPGRADE), KeyWord_my_plan)

                //VIEW_TRANSECTION
                //Payment/ Payment receipt/
                val keyword_View_Transection = KeyWord(9, Constant.VIEW_TRANSECTION, Constant.VIEW_TRANSECTION, true, true)
                put(KeyWordKey(9, Constant.PAYMENT), keyword_View_Transection)
                put(KeyWordKey(9, Constant.PAYMENT_RECEIPT), keyword_View_Transection)

                //VIEW_TOP_UP_OFFERS
                //Top Up, Upgrade
                val KeyWord_View_Top_Up_offers = KeyWord(10, Constant.VIEW_TOP_UP_OFFERS, Constant.VIEW_TOP_UP_OFFERS, true, true)
                put(KeyWordKey(10, Constant.TOP_UP), KeyWord_View_Top_Up_offers)
                put(KeyWordKey(10, Constant.UPGRADE), KeyWord_View_Top_Up_offers)

                //VIEW_TOP_UP_OFFERS
                //KeyWord_Top_Up_availed

                val KeyWord_Top_Up_availed = KeyWord(11, Constant.TOP_UP_AVAILED, Constant.TOP_UP_AVAILED, true, true)
                put(KeyWordKey(11, Constant.TOP_UP), KeyWord_Top_Up_availed)
                put(KeyWordKey(11, Constant.NEW_TOP_UP), KeyWord_Top_Up_availed)
                put(KeyWordKey(11, Constant.DATA_REQUIRED), KeyWord_Top_Up_availed)
                put(KeyWordKey(11, Constant.TOP_UP_STATUS), KeyWord_Top_Up_availed)


                //CHECK_AUTO_PAY_STATUS
                //Pay/Payment/AutoPay/Set up /Auto debit
                val Keyword_CheckAuto_Pay = KeyWord(12, Constant.CHECK_AUTO_PAY_STATUS, Constant.CHECK_AUTO_PAY_STATUS, true, true)
                put(KeyWordKey(12, Constant.PAY), Keyword_CheckAuto_Pay)
                put(KeyWordKey(12, Constant.PAYMENT), Keyword_CheckAuto_Pay)
                put(KeyWordKey(12, Constant.AUTO_PAY), Keyword_CheckAuto_Pay)
                put(KeyWordKey(12, Constant.SET_UP), Keyword_CheckAuto_Pay)
                put(KeyWordKey(12, Constant.AUTO_DEBIT), Keyword_CheckAuto_Pay)

                val Keyword_Auto_Pay = KeyWord(13, Constant.ADD_AUTO_PAY, Constant.ADD_AUTO_PAY, true, true)
                put(KeyWordKey(13, Constant.PAY), Keyword_CheckAuto_Pay)
                put(KeyWordKey(13, Constant.PAYMENT), Keyword_CheckAuto_Pay)
                put(KeyWordKey(13, Constant.AUTO_PAY), Keyword_CheckAuto_Pay)
                put(KeyWordKey(13, Constant.SET_UP), Keyword_CheckAuto_Pay)
                put(KeyWordKey(13, Constant.AUTO_DEBIT), Keyword_CheckAuto_Pay)

                //VIEW_CONTACT
                //Customer name/ Can id/ RMN/Registered no/ Registered Mobile Number/ Mobile no/ Contact no/ Address/ Phone no/ Email id/ Change
                val KeyWord_View_Contact = KeyWord(14, Constant.VIEW_CONTACT, Constant.VIEW_CONTACT, true, true)
                put(KeyWordKey(14, Constant.CUSTOMER_CARE), KeyWord_View_Contact)
                put(KeyWordKey(14, Constant.CAN_ID), KeyWord_View_Contact)
                put(KeyWordKey(14, Constant.RMN), KeyWord_View_Contact)
                put(KeyWordKey(14, Constant.REGISTERED_MOBILE_NO), KeyWord_View_Contact)
                put(KeyWordKey(14, Constant.MOBILE_NO), KeyWord_View_Contact)
                put(KeyWordKey(14, Constant.REGISTERED_NO), KeyWord_View_Contact)
                put(KeyWordKey(14, Constant.CONTACT_NO), KeyWord_View_Contact)
                put(KeyWordKey(14, Constant.ADDRESS), KeyWord_View_Contact)
                put(KeyWordKey(14, Constant.PHONE_NO), KeyWord_View_Contact)
                put(KeyWordKey(14, Constant.EMAIL_ID), KeyWord_View_Contact)


                //VIEW_MRTG
                //ask navigation
                //VIEW_PLAN_CHANGE_OFFER
                val KeyWord_plan_change_offer = KeyWord(15, Constant.VIEW_PLAN_CHANGE_OFFER, Constant.VIEW_PLAN_CHANGE_OFFER, true, true)
                put(KeyWordKey(15, Constant.PLAN_DETAILS), KeyWord_plan_change_offer)
                put(KeyWordKey(15, Constant.DATA), KeyWord_plan_change_offer)
                put(KeyWordKey(15, Constant.SPEED), KeyWord_plan_change_offer)
                put(KeyWordKey(15, Constant.INVOICE), KeyWord_plan_change_offer)
                put(KeyWordKey(15, Constant.BILL), KeyWord_plan_change_offer)
                put(KeyWordKey(15, Constant.PAYMENT), KeyWord_plan_change_offer)
                put(KeyWordKey(15, Constant.NEW_PLAN), KeyWord_plan_change_offer)
                put(KeyWordKey(15, Constant.UPGRADE), KeyWord_plan_change_offer)
                put(KeyWordKey(15, Constant.CHANGE_PLAN), KeyWord_plan_change_offer)
                put(KeyWordKey(15, Constant.NEW_OFFER), KeyWord_plan_change_offer)
                put(KeyWordKey(15, Constant.NEW_SCHEME), KeyWord_plan_change_offer)

//
//                //FORGOT_PASSWORD
                val KeyWord_forgot_password = KeyWord(16, Constant.FORGOT_PASSWORD, Constant.FORGOT_PASSWORD, true, true)
//                put(KeyWordKey(16, "Password"), KeyWord_forgot_password)
//                put(KeyWordKey(16, "Otp Password"), KeyWord_forgot_password)
//                put(KeyWordKey(16, "Change Password"), KeyWord_forgot_password)
//                put(KeyWordKey(16, "Update Password"), KeyWord_forgot_password)
//

                //CREATE_SR
                val KeyWord_ceate_sr = KeyWord(17, Constant.CREATE_SR, Constant.CREATE_SR, true, true)
                put(KeyWordKey(17, Constant.RAISE_NEW_SR), KeyWord_ceate_sr)
                put(KeyWordKey(17, Constant.NEW_SR), KeyWord_ceate_sr)
                put(KeyWordKey(17, Constant.COMPLAINT), KeyWord_ceate_sr)
                put(KeyWordKey(17, Constant.ISSUE), KeyWord_ceate_sr)
                put(KeyWordKey(17, Constant.RAISE_CONCERN), KeyWord_ceate_sr)

                //FORGOT_PASSWORD
                //ask navigation
                val keyword_track_my_order = KeyWord(18, Constant.TRACK_MY_ORDER, Constant.TRACK_MY_ORDER, true, true)
                put(KeyWordKey(18, Constant.ACCOUNT_STATUS), keyword_track_my_order)
                put(KeyWordKey(18, Constant.MY_ACCOUNT), keyword_track_my_order)

                //FORGOT_PASSWORD
                val keyword_change_password = KeyWord(19, Constant.CHANGE_PASSWORD, Constant.CHANGE_PASSWORD, true, true)
                put(KeyWordKey(19, Constant.PASSWORD), keyword_change_password)
                put(KeyWordKey(19, Constant.OTP_PASSWORD), keyword_change_password)
                put(KeyWordKey(19, Constant.CHANGE_PASSWORD), keyword_change_password)
                put(KeyWordKey(19, Constant.UPDATE_PASSWORD), keyword_change_password)


                //FORGOT_PASSWORD
                val keyword_update_mobile_number = KeyWord(20, Constant.UPDATE_MOBILE_NUMBER, Constant.UPDATE_MOBILE_NUMBER, true, true)
                put(KeyWordKey(20, Constant.RMN), keyword_update_mobile_number)
                put(KeyWordKey(20, Constant.REGISTERED_MOBILE_NO), keyword_update_mobile_number)
                put(KeyWordKey(20, Constant.CONTACT_DETAILS), keyword_update_mobile_number)
                put(KeyWordKey(20, Constant.UPDATE_NEW_NUMBER), keyword_update_mobile_number)
                put(KeyWordKey(20, Constant.UPDATE_PHONE_NUMBER), keyword_update_mobile_number)
                put(KeyWordKey(20, Constant.UPDATE_CONTACT_DETAILS), keyword_update_mobile_number)


                //FORGOT_PASSWORD
                val keyword_add_contact = KeyWord(21, Constant.ADD_CONTACT, Constant.ADD_CONTACT, true, true)
                put(KeyWordKey(21, Constant.RMN), keyword_update_mobile_number)
                put(KeyWordKey(21, Constant.REGISTERED_MOBILE_NO), keyword_update_mobile_number)
                put(KeyWordKey(21, Constant.CONTACT_DETAILS), keyword_update_mobile_number)
                put(KeyWordKey(21, Constant.UPDATE_NEW_NUMBER), keyword_update_mobile_number)
                put(KeyWordKey(21, Constant.UPDATE_PHONE_NUMBER), keyword_update_mobile_number)
                put(KeyWordKey(21, Constant.UPDATE_CONTACT_DETAILS), keyword_update_mobile_number)


                //UPDATE_EMAIL_ID

                val keyword_update_email = KeyWord(22, Constant.UPDATE_EMAIL_ID, Constant.UPDATE_EMAIL_ID, true, true)
                put(KeyWordKey(22, Constant.EMAIL_ID), keyword_update_email)
                put(KeyWordKey(22, Constant.REGISTERED_EMAIL_ID), keyword_update_email)
                put(KeyWordKey(22, Constant.CHANGE_EMAIL_ID), keyword_update_email)
                put(KeyWordKey(22, Constant.NEW_EMAIL_ID), keyword_update_email)

                val keyword_multipla_acc = KeyWord(23, Constant.LINK_MULTIPLE_ACCOUNT, Constant.LINK_MULTIPLE_ACCOUNT, true, true)
                put(KeyWordKey(23, Constant.ADD_ANOTHER_ACCOUNT), keyword_multipla_acc)
                put(KeyWordKey(23, Constant.ADD_ANOTHER_CAN_ID), keyword_multipla_acc)
                put(KeyWordKey(23, Constant.NEW_ACCOUNT), keyword_multipla_acc)
                put(KeyWordKey(23, Constant.ADD_CAN_ID), keyword_multipla_acc)
//
//                put(KeyWordKey(1, "Add Another Account"), keyword_multipla_acc)
//                put(KeyWordKey(1, "Add another CAN id"), keyword_multipla_acc)
//                put(KeyWordKey(1, "Attach another account"), keyword_multipla_acc)
//                put(KeyWordKey(1, "New account"), keyword_multipla_acc)
//                put(KeyWordKey(1, "Add Can id"), keyword_multipla_acc)

//                login_via_mobile
                val keyword_login_via_mobile = KeyWord(24, Constant.LINK_MULTIPLE_ACCOUNT,  Constant.LINK_MULTIPLE_ACCOUNT, true, true)
                put(KeyWordKey(24, Constant.RMN), keyword_login_via_mobile)
                put(KeyWordKey(24, Constant.REGISTERED_MOBILE_NO), keyword_login_via_mobile)
                put(KeyWordKey(24, Constant.MOBILE_NO), keyword_login_via_mobile)
                put(KeyWordKey(24, Constant.CONTACT_DETAILS), keyword_login_via_mobile)

                //login_via_mobile
                //need to disccuss
//                val keyword_login_via_UserNAME_AND_PASSWORD = KeyWord(25, Constant.LOGIN_VIA_USER_NAME_PASSWORD, "LOGIN_VIA_USER_NAME_PASSWORD", true, true)
//                put(KeyWordKey(25, Constant.LOGIN), keyword_login_via_UserNAME_AND_PASSWORD)
//                put(KeyWordKey(25, Constant.CREDENTIAL), keyword_login_via_UserNAME_AND_PASSWORD)
//                put(KeyWordKey(25, Constant.USERNAME), keyword_login_via_UserNAME_AND_PASSWORD)
//                put(KeyWordKey(25, Constant.PASSWORD), keyword_login_via_UserNAME_AND_PASSWORD)


                //data/ Data consumed/Data check/ Data volume/ payment/ option/ new plan/ bill cycle/check my
                // bill/ change plan/ tariff/ self care portal/self care/shift different room/shift within the society/
                // transfer ownership/ transfer/ FUP/ FTTH/ symmetric speed/ asymmetric bandwidth/IP address/ static/ dynamic/service availability/
                // GB data/data consumed/ check usage/ identify proof/ address/Wi-Fi/
                // router/ spectra voice

                val keyword_faq = KeyWord(26, Constant.FAQ, Constant.FAQ, true, true)
                put(KeyWordKey(26, Constant.DATA), keyword_faq)
                put(KeyWordKey(26, Constant.DATA_CONSUMED), keyword_faq)
                put(KeyWordKey(26, Constant.DATA_CHECK), keyword_faq)
                put(KeyWordKey(26, Constant.DATA_VOLUME), keyword_faq)
                put(KeyWordKey(26, Constant.PAYMENT), keyword_faq)
                put(KeyWordKey(26, Constant.OPTION), keyword_faq)
                put(KeyWordKey(26, Constant.NEW_PLAN), keyword_faq)
                put(KeyWordKey(26, Constant.BILL_CYCLE), keyword_faq)
                put(KeyWordKey(26, Constant.CHECK_MY_BILL), keyword_faq)
                put(KeyWordKey(26, Constant.CHANGE_PLAN), keyword_faq)
                put(KeyWordKey(26, Constant.TARIFF), keyword_faq)
                put(KeyWordKey(26, Constant.SELF_CARE_PORTAL), keyword_faq)
                put(KeyWordKey(26, Constant.SELF_CARE), keyword_faq)
                put(KeyWordKey(26, Constant.SHIFT_DIFFERENT_ROOM), keyword_faq)
                put(KeyWordKey(26, Constant.SHIFT_WITHIN_THE_SOCIETY), keyword_faq)
                put(KeyWordKey(26, Constant.TRANSFER_OWNERSHIP), keyword_faq)
                put(KeyWordKey(26, Constant.TRANSFER), keyword_faq)
                put(KeyWordKey(26, Constant.FUP), keyword_faq)
                put(KeyWordKey(26, Constant.FTTH), keyword_faq)
                put(KeyWordKey(26, Constant.SYMMETRIC_SPEED), keyword_faq)
                put(KeyWordKey(26, Constant.ASYMMETRIC_BANDWIDTH), keyword_faq)
                put(KeyWordKey(26, Constant.IP_ADDRESS), keyword_faq)
                put(KeyWordKey(26, Constant.STATIC), keyword_faq)
                put(KeyWordKey(26, Constant.DYNAMIC), keyword_faq)
                put(KeyWordKey(26, Constant.SERVICE_AVAILABILITY), keyword_faq)
                put(KeyWordKey(26, Constant.GB_DATA), keyword_faq)
                put(KeyWordKey(26, Constant.DATA_USAGE), keyword_faq)
                put(KeyWordKey(26, Constant.IDENTIFY_PROOF), keyword_faq)
                put(KeyWordKey(26, Constant.ADDRESS), keyword_faq)
                put(KeyWordKey(26, Constant.WI_FI), keyword_faq)
                put(KeyWordKey(26, Constant.ROUTER), keyword_faq)
                put(KeyWordKey(26, Constant.SPECTRA_VOICE), keyword_faq)

            }

            return keyWord
        }


}
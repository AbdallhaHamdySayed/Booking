package com.AlTaraf.Booking.response.keys;

public enum ErrorStatus {

    SYSTEM_HAVE_ISSUE("SYSTEM_HAVE_ISSUE", "يوجد مشكله فى النظام يرجى مراجعه الادمن", "System Error Call System Admin  ", "", ""),
    INVALID_JWT_SIGNATURE("INVALID_JWT_SIGNATURE", " مشكله فى ال JWT", "invalid jwt signature", "", ""),
    INVALID_JWT_TOKEN("INVALID_JWT_TOKEN", " مشكله فى ال JWT", "INVALID JWT TOKEN", "", ""),
    EXPIRED_JWT_EXCEPTION("EXPIRED_JWT_EXCEPTION", " تم انتهاء الصلاحيه يرجى تسجيل الدخول", "expired jwt exception", "", ""),
    JWT_EXCEPTION("JWT_EXCEPTION", "الصلاحيه غير فعاله", "jwt exception", "", ""),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", "اﻻكونت غير فعال / معلق", "Account Is Locked", "", ""),
    ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND", "بيانات الدخول غير صحيحه", "Login Data Is incorrect..", "", ""),
    USER_EMAIL_TOKEN("USER_EMAIL_TOKEN", "اليوزر نيم مستخدم من قبل ", "User Name Already token ..", "", ""),
    ACCOUNT_MUST_CHANGE_PASSWORD_TO_ENABLE_REFRESH_TOKEN("ACCOUNT_MUST_CHANGE_PASSWORD_TO_ENABLE_REFRESH_TOKEN", "يجب اجراء عمليه تغير الباسورد ليتم استكمال العمليه  ", "User Must Change Password Yto Enable New Refresh Token", "", ""),
    HAVE_RELATED_DATA("HAVE_RELATED_DATA", "يوجد بيانات اخرى متعلقه ", "Delete Not Allowed ", "لايمكن الحذف لوجود بيانات متعلقه بالسجل", "Have Child Data Delete Not Allowed  "),
    HAVE_DUPLICATE_DATA("HAVE_DUPLICATE_DATA", "يوجد بيانات مكرره سيتم التراجع عن العمليه ", "  ", "يوجد بيانات مكرره سيتم التراجع عن العمليه ", "Have Child Data Delete Not Allowed  "),
    NOT_ALLOWED_TO_WITHDROW("NOT_ALLOWED_TO_WITHDROW", " المبلغ المطلوب غير مسموح", "not allowed to withdraw", "", ""),
    CURRENT_BALANCE_IS_INSUFFICIENT("CURRENT_BALANCE_IS_INSUFFICIENT", "الرصيد الحالى غير كافى برجاء شحن االرصيد", "please recharge your balance", "", ""),
    NOT_ALLOWED_TO_COMPLETE_BECAUSE_OF_EXCEPTION("NOT_ALLOWED_TO_COMPLETE_BECAUSE_OF_EXCEPTION", "يوجد خطأ برمجى", "found error Exception", "", ""),
    UNABLE_TO_DELETE_CUSTOMER("UNABLE_TO_DELETE_CUSTOMER","لا يمكن حذف العميل","Unable to delete the customer","",""),
    DATA_ALREADY_EXISTS("DATA_ALREADY_EXISTS", "بعض البيانات المقدمة موجودة بالفعل", "Some of the provided data already exist", "", ""),
    INVALID_OTP("INVALID_OTP", "رمز الدخول غير صحيح", "Invalid otp", "", ""),
    NOT_ACCEPTABLE_PROFIT_AMOUNT("NOT_ACCEPTABLE_PROFIT_AMOUNT",   "القيمه غير مقبوله ", "There are no profits for this client", "", ""  ),
    VALUE_GREATER_THAN_PROFIT("VALUE_GREATER_THAN_PROFIT", "لاتوجد تلك القيمة في محفظة الأرباح", "This value is not in the profit wallet", "", ""),
    PASSWORD_NOT_CORRECT("PASSWORD_NOT_CORRECT", "باسورد غير صحيح" , "Password is not correct", "" , ""),
    ACCOUNT_NOT_ACTIVE("ACCOUNT_NOT_ACTIVE", "هذا الحساب غير مفعل" , "Account is not active", "" , ""),
    ACCOUNT_IS_BAN("ACCOUNT_IS_BAN", "هذا الحساب موقوف" , "This account is suspended", "" , ""),
    ROLE_INVALID("ACCOUNT_IS_INVALID", "صفة الحساب غير صحيحة يرجي التأكد من صفة الحساب" , "The account description is incorrect. Please check the account description.", "" , ""),
    NUMBER_NOT_REGISTERED("NUMBER_NOT_REGISTERED", "هذا الرقم غير مسجل في التطبيق", "This is number not registered on Application", "" , "");
    private final String errorKey;
    private final String arabicMessage;
    private final String englishMessage;
    private final String fixAr;
    private final String fixEn;

    ErrorStatus(String errorKey, String arabicMessage, String englishMessage, String fixAr, String fixEn) {
        this.errorKey = errorKey;
        this.arabicMessage = arabicMessage;
        this.englishMessage = englishMessage;
        this.fixAr = fixAr;
        this.fixEn = fixEn;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public String getArabicMessage() {
        return arabicMessage;
    }

    public String getEnglishMessage() {
        return englishMessage;
    }

    public String getFixAr() {
        return fixAr;
    }

    public String getFixEn() {
        return fixEn;
    }
}

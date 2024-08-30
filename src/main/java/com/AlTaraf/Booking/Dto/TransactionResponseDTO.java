package com.AlTaraf.Booking.Dto;

import java.util.List;

public class TransactionResponseDTO {
    private String result;
    private Data data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String customer_phone;
        private String customer_email;
        private String customer_name;
        private String owner_name;
        private String shop_name;
        private String shop_logo;
        private String shop_url;
        private String owner_city;
        private String owner_phone;
        private String owner_email;
        private String amount;
        private String currency;
        private String gateway_name;
        private String gateway;
        private String gateway_ref;
        private String date_time;
        private String created_at;
        private int order_status;
        private String order_type;
        private List<Object> order_history;
        private String notes_to_customer;
        private NotesToShop notes_to_shop;
        private String reference;
        private String order_id;
        private String custom_ref;

        public String getCustomer_phone() {
            return customer_phone;
        }

        public void setCustomer_phone(String customer_phone) {
            this.customer_phone = customer_phone;
        }

        public String getCustomer_email() {
            return customer_email;
        }

        public void setCustomer_email(String customer_email) {
            this.customer_email = customer_email;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public String getOwner_name() {
            return owner_name;
        }

        public void setOwner_name(String owner_name) {
            this.owner_name = owner_name;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public String getShop_url() {
            return shop_url;
        }

        public void setShop_url(String shop_url) {
            this.shop_url = shop_url;
        }

        public String getOwner_city() {
            return owner_city;
        }

        public void setOwner_city(String owner_city) {
            this.owner_city = owner_city;
        }

        public String getOwner_phone() {
            return owner_phone;
        }

        public void setOwner_phone(String owner_phone) {
            this.owner_phone = owner_phone;
        }

        public String getOwner_email() {
            return owner_email;
        }

        public void setOwner_email(String owner_email) {
            this.owner_email = owner_email;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getGateway_name() {
            return gateway_name;
        }

        public void setGateway_name(String gateway_name) {
            this.gateway_name = gateway_name;
        }

        public String getGateway() {
            return gateway;
        }

        public void setGateway(String gateway) {
            this.gateway = gateway;
        }

        public String getGateway_ref() {
            return gateway_ref;
        }

        public void setGateway_ref(String gateway_ref) {
            this.gateway_ref = gateway_ref;
        }

        public String getDate_time() {
            return date_time;
        }

        public void setDate_time(String date_time) {
            this.date_time = date_time;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public List<Object> getOrder_history() {
            return order_history;
        }

        public void setOrder_history(List<Object> order_history) {
            this.order_history = order_history;
        }

        public String getNotes_to_customer() {
            return notes_to_customer;
        }

        public void setNotes_to_customer(String notes_to_customer) {
            this.notes_to_customer = notes_to_customer;
        }

        public NotesToShop getNotes_to_shop() {
            return notes_to_shop;
        }

        public void setNotes_to_shop(NotesToShop notes_to_shop) {
            this.notes_to_shop = notes_to_shop;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getCustom_ref() {
            return custom_ref;
        }

        public void setCustom_ref(String custom_ref) {
            this.custom_ref = custom_ref;
        }

        public static class NotesToShop {
            private String payment_status;

            public String getPayment_status() {
                return payment_status;
            }

            public void setPayment_status(String payment_status) {
                this.payment_status = payment_status;
            }
        }
    }
}
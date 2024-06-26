package localhost.resfull_booker_model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RestFullPojo {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("firstname")
        private String firstname;
        @JsonProperty("lastname")
        private String lastname;
        @JsonProperty("totalprice")
        private Integer totalprice;
        @JsonProperty("depositpaid")
        private Boolean depositpaid;
        @JsonProperty("bookingdates")
        private String bookingdates;
        @JsonProperty("checkin")
        private String checkin;
        @JsonProperty("checkout")
        private String checkout;
        @JsonProperty("additionalneeds")
        private String additionalneeds;

        @JsonProperty("firstname")
        public String getFirstname() {
                return firstname;
        }

        @JsonProperty("firstname")
        public void setFirstname(String firstname) {
                this.firstname = firstname;
        }

        @JsonProperty("lastname")
        public String getLastname() {
                return lastname;
        }

        @JsonProperty("lastname")
        public void setLastname(String lastname) {
                this.lastname = lastname;
        }

        @JsonProperty("totalprice")
        public Integer getTotalprice() {
                return totalprice;
        }

        @JsonProperty("totalprice")
        public void setTotalprice(Integer totalprice) {
                this.totalprice = totalprice;
        }

        @JsonProperty("depositpaid")
        public Boolean getDepositpaid() {
                return depositpaid;
        }

        @JsonProperty("depositpaid")
        public void setDepositpaid(Boolean depositpaid) {
                this.depositpaid = depositpaid;
        }

        @JsonProperty("bookingdates")
        public String getBookingdates() {
                return bookingdates;
        }

        @JsonProperty("bookingdates")
        public void setBookingdates(List<String> bookingdates) {
                this.bookingdates = String.valueOf(bookingdates);
        }
        @JsonProperty("checkin")
        public String getCheckin() {
                return checkin;
        }

        @JsonProperty("checkin")
        public void setCheckin(String checkin) {
                this.checkin = checkin;
        }

        @JsonProperty("checkout")
        public String getCheckout() {
                return checkout;
        }

        @JsonProperty("checkout")
        public void setCheckout(String checkout) {
                this.checkout = checkout;
        }

        @JsonProperty("additionalneeds")
        public String getAdditionalneeds() {
                return additionalneeds;
        }

        @JsonProperty("additionalneeds")
        public void setAdditionalneeds(String additionalneeds) {
                this.additionalneeds = additionalneeds;
        }

}



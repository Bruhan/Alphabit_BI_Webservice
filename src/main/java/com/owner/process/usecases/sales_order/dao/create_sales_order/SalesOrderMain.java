
package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

//import java.util.Date;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SalesOrderMain {

    private long id;
    private String email;
    @JsonProperty("closed_at")
    private String closedAt;


    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;
    private long number;
    private String note;
    private String token;
    private String gateway;
    private boolean test;
    @JsonProperty("total_price")
    private String totalPrice;
    @JsonProperty("subtotal_price")
    private String subtotalPrice;
    @JsonProperty("total_weight")
    private long totalWeight;
    @JsonProperty("total_tax")
    private String totalTax;
    @JsonProperty("taxes_included")
    private boolean taxesIncluded;
    private String currency;

    @JsonProperty("current_subtotal_price")
    private String currentSubtotalPrice;
    @JsonProperty("current_subtotal_price_set")
    private CurrentSubtotalPriceSet currentSubtotalPriceSet;

    @JsonProperty("current_total_discounts")
    private String currentTotalDiscounts;
    @JsonProperty("current_total_discounts_set")
    private CurrentTotalDiscountsSet currentTotalDiscountsSet;

    @JsonProperty("current_total_price")
    private String currentTotalPrice;
    @JsonProperty("current_total_price_set")
    private CurrentTotalPriceSet currentTotalPriceSet;

    @JsonProperty("current_total_tax")
    private String currentTotalTax;
    @JsonProperty("current_total_tax_set")
    private CurrentTotalTaxSet currentTotalTaxSet;


    @JsonProperty("financial_status")
    private String financialStatus;
    private boolean confirmed;
    @JsonProperty("total_discounts")
    private String totalDiscounts;
    @JsonProperty("total_line_items_price")
    private String totalLineItemsPrice;
    @JsonProperty("cart_token")
    private String cartToken;
    @JsonProperty("buyer_accepts_marketing")
    private boolean buyerAcceptsMarketing;
    private String name;
    @JsonProperty("referring_site")
    private String referringSite;
    @JsonProperty("landing_site")
    private String landingSite;
    @JsonProperty("cancelled_at")
    private String cancelledAt;
    @JsonProperty("cancel_reason")
    private String cancelReason;
    @JsonProperty("total_price_usd")
    private String totalPriceUsd;
    @JsonProperty("checkout_token")
    private String checkoutToken;
    private String reference;
    @JsonProperty("user_id")
    private long userId;
    @JsonProperty("location_id")
    private String locationId;
    @JsonProperty("source_identifier")
    private String sourceIdentifier;
    @JsonProperty("source_url")
    private String sourceUrl;

    @JsonProperty("processed_at")
    private String processedAt;

    @JsonProperty("device_id")
    private String deviceId;
    private String phone;
    @JsonProperty("customer_locale")
    private String customerLocale;
    @JsonProperty("app_id")
    private long appId;
    @JsonProperty("browser_ip")
    private String browserIp;
    @JsonProperty("landing_site_ref")
    private String landingSiteRef;
    @JsonProperty("order_number")
    private long orderNumber;
    @JsonProperty("discount_applications")
    private List<DiscountApplications> discountApplications;
    @JsonProperty("discount_codes")
    private List<DiscountCodes> discountCodes;
    @JsonProperty("note_attributes")
    private List<NoteAttributes> noteAttributes;
    @JsonProperty("payment_gateway_names")
    private List<String> paymentGatewayNames;
    @JsonProperty("processing_method")
    private String processingMethod;
    @JsonProperty("checkout_id")
    private long checkoutId;
    @JsonProperty("source_name")
    private String sourceName;
    @JsonProperty("fulfillment_status")
    private String fulfillmentStatus;
    @JsonProperty("tax_lines")
    private List<TaxLines> taxLines;
    private String tags;
    @JsonProperty("contact_email")
    private String contactEmail;
    @JsonProperty("order_status_url")
    private String orderStatusUrl;
    @JsonProperty("presentment_currency")
    private String presentmentCurrency;
    @JsonProperty("total_line_items_price_set")
    private TotalLineItemsPriceSet totalLineItemsPriceSet;
    @JsonProperty("total_discounts_set")
    private TotalDiscountsSet totalDiscountsSet;
    @JsonProperty("total_shipping_price_set")
    private TotalShippingPriceSet totalShippingPriceSet;
    @JsonProperty("subtotal_price_set")
    private SubtotalPriceSet subtotalPriceSet;

    @JsonProperty("total_outstanding")
    private String totalOutstanding;


    @JsonProperty("total_price_set")
    private TotalPriceSet totalPriceSet;
    @JsonProperty("total_tax_set")
    private TotalTaxSet totalTaxSet;
    @JsonProperty("line_items")
    private List<LineItems> lineItems;
    private List<Fulfillments> fulfillments;
    private List<Refunds> refunds;
    private List<Refunds> refundsList;
    @JsonProperty("total_tip_received")
    private String totalTipReceived;
    @JsonProperty("original_total_duties_set")
    private OriginalTotalDutiesSet originalTotalDutiesSet;
    @JsonProperty("current_total_duties_set")
    private CurrentTotalDutiesSet currentTotalDutiesSet;
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    //    @JsonProperty("shipping_lines")
//    private List<String> shippingLines;
    @JsonProperty("shipping_lines")
    private List<ShippingLines> shippingLines;
    @JsonProperty("billing_address")
    private BillingAddress billingAddress;
    @JsonProperty("shipping_address")
    private ShippingAddress shippingAddress;
    @JsonProperty("client_details")
    private ClientDetails clientDetails;
    @JsonProperty("payment_details")
    private PaymentDetails paymentDetails;
    private Customer customer;

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getClosedAt() {
//        return closedAt;
//    }
//
//    public void setClosedAt(String closedAt) {
//        this.closedAt = closedAt;
//    }
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public long getNumber() {
//        return number;
//    }
//
//    public void setNumber(long number) {
//        this.number = number;
//    }
//
//    public String getNote() {
//        return note;
//    }
//
//    public void setNote(String note) {
//        this.note = note;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public String getGateway() {
//        return gateway;
//    }
//
//    public void setGateway(String gateway) {
//        this.gateway = gateway;
//    }
//
//    public boolean getTest() {
//        return test;
//    }
//
//    public void setTest(boolean test) {
//        this.test = test;
//    }
//
//    public String getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(String totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//
//    public String getSubtotalPrice() {
//        return subtotalPrice;
//    }
//
//    public void setSubtotalPrice(String subtotalPrice) {
//        this.subtotalPrice = subtotalPrice;
//    }
//
//    public long getTotalWeight() {
//        return totalWeight;
//    }
//
//    public void setTotalWeight(long totalWeight) {
//        this.totalWeight = totalWeight;
//    }
//
//    public String getTotalTax() {
//        return totalTax;
//    }
//
//    public void setTotalTax(String totalTax) {
//        this.totalTax = totalTax;
//    }
//
//    public boolean getTaxesIncluded() {
//        return taxesIncluded;
//    }
//
//    public void setTaxesIncluded(boolean taxesIncluded) {
//        this.taxesIncluded = taxesIncluded;
//    }
//
//    public String getCurrency() {
//        return currency;
//    }
//
//    public void setCurrency(String currency) {
//        this.currency = currency;
//    }
//
//    public String getFinancialStatus() {
//        return financialStatus;
//    }
//
//    public void setFinancialStatus(String financialStatus) {
//        this.financialStatus = financialStatus;
//    }
//
//    public boolean getConfirmed() {
//        return confirmed;
//    }
//
//    public void setConfirmed(boolean confirmed) {
//        this.confirmed = confirmed;
//    }
//
//    public String getTotalDiscounts() {
//        return totalDiscounts;
//    }
//
//    public void setTotalDiscounts(String totalDiscounts) {
//        this.totalDiscounts = totalDiscounts;
//    }
//
//    public String getTotalLineItemsPrice() {
//        return totalLineItemsPrice;
//    }
//
//    public void setTotalLineItemsPrice(String totalLineItemsPrice) {
//        this.totalLineItemsPrice = totalLineItemsPrice;
//    }
//
//    public String getCartToken() {
//        return cartToken;
//    }
//
//    public void setCartToken(String cartToken) {
//        this.cartToken = cartToken;
//    }
//
//    public boolean getBuyerAcceptsMarketing() {
//        return buyerAcceptsMarketing;
//    }
//
//    public void setBuyerAcceptsMarketing(boolean buyerAcceptsMarketing) {
//        this.buyerAcceptsMarketing = buyerAcceptsMarketing;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getReferringSite() {
//        return referringSite;
//    }
//
//    public void setReferringSite(String referringSite) {
//        this.referringSite = referringSite;
//    }
//
//    public String getLandingSite() {
//        return landingSite;
//    }
//
//    public void setLandingSite(String landingSite) {
//        this.landingSite = landingSite;
//    }
//
//    public String getCancelledAt() {
//        return cancelledAt;
//    }
//
//    public void setCancelledAt(String cancelledAt) {
//        this.cancelledAt = cancelledAt;
//    }
//
//    public String getCancelReason() {
//        return cancelReason;
//    }
//
//    public void setCancelReason(String cancelReason) {
//        this.cancelReason = cancelReason;
//    }
//
//    public String getTotalPriceUsd() {
//        return totalPriceUsd;
//    }
//
//    public void setTotalPriceUsd(String totalPriceUsd) {
//        this.totalPriceUsd = totalPriceUsd;
//    }
//
//    public String getCheckoutToken() {
//        return checkoutToken;
//    }
//
//    public void setCheckoutToken(String checkoutToken) {
//        this.checkoutToken = checkoutToken;
//    }
//
//    public String getReference() {
//        return reference;
//    }
//
//    public void setReference(String reference) {
//        this.reference = reference;
//    }
//
//    public long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(long userId) {
//        this.userId = userId;
//    }
//
//    public String getLocationId() {
//        return locationId;
//    }
//
//    public void setLocationId(String locationId) {
//        this.locationId = locationId;
//    }
//
//    public String getSourceIdentifier() {
//        return sourceIdentifier;
//    }
//
//    public void setSourceIdentifier(String sourceIdentifier) {
//        this.sourceIdentifier = sourceIdentifier;
//    }
//
//    public String getSourceUrl() {
//        return sourceUrl;
//    }
//
//    public void setSourceUrl(String sourceUrl) {
//        this.sourceUrl = sourceUrl;
//    }
//
//    public Date getProcessedAt() {
//        return processedAt;
//    }
//
//    public void setProcessedAt(Date processedAt) {
//        this.processedAt = processedAt;
//    }
//
//    public String getDeviceId() {
//        return deviceId;
//    }
//
//    public void setDeviceId(String deviceId) {
//        this.deviceId = deviceId;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getCustomerLocale() {
//        return customerLocale;
//    }
//
//    public void setCustomerLocale(String customerLocale) {
//        this.customerLocale = customerLocale;
//    }
//
//    public long getAppId() {
//        return appId;
//    }
//
//    public void setAppId(long appId) {
//        this.appId = appId;
//    }
//
//    public String getBrowserIp() {
//        return browserIp;
//    }
//
//    public void setBrowserIp(String browserIp) {
//        this.browserIp = browserIp;
//    }
//
//    public String getLandingSiteRef() {
//        return landingSiteRef;
//    }
//
//    public void setLandingSiteRef(String landingSiteRef) {
//        this.landingSiteRef = landingSiteRef;
//    }
//
//    public long getOrderNumber() {
//        return orderNumber;
//    }
//
//    public void setOrderNumber(long orderNumber) {
//        this.orderNumber = orderNumber;
//    }
//
//    public List<String> getDiscountApplications() {
//        return discountApplications;
//    }
//
//    public void setDiscountApplications(List<String> discountApplications) {
//        this.discountApplications = discountApplications;
//    }
//
//    public List<String> getDiscountCodes() {
//        return discountCodes;
//    }
//
//    public void setDiscountCodes(List<String> discountCodes) {
//        this.discountCodes = discountCodes;
//    }
//
//    public List<String> getNoteAttributes() {
//        return noteAttributes;
//    }
//
//    public void setNoteAttributes(List<String> noteAttributes) {
//        this.noteAttributes = noteAttributes;
//    }
//
//    public List<String> getPaymentGatewayNames() {
//        return paymentGatewayNames;
//    }
//
//    public void setPaymentGatewayNames(List<String> paymentGatewayNames) {
//        this.paymentGatewayNames = paymentGatewayNames;
//    }
//
//    public String getProcessingMethod() {
//        return processingMethod;
//    }
//
//    public void setProcessingMethod(String processingMethod) {
//        this.processingMethod = processingMethod;
//    }
//
//    public long getCheckoutId() {
//        return checkoutId;
//    }
//
//    public void setCheckoutId(long checkoutId) {
//        this.checkoutId = checkoutId;
//    }
//
//    public String getSourceName() {
//        return sourceName;
//    }
//
//    public void setSourceName(String sourceName) {
//        this.sourceName = sourceName;
//    }
//
//    public String getFulfillmentStatus() {
//        return fulfillmentStatus;
//    }
//
//    public void setFulfillmentStatus(String fulfillmentStatus) {
//        this.fulfillmentStatus = fulfillmentStatus;
//    }
//
//    public List<TaxLines> getTaxLines() {
//        return taxLines;
//    }
//
//    public void setTaxLines(List<TaxLines> taxLines) {
//        this.taxLines = taxLines;
//    }
//
//    public String getTags() {
//        return tags;
//    }
//
//    public void setTags(String tags) {
//        this.tags = tags;
//    }
//
//    public String getContactEmail() {
//        return contactEmail;
//    }
//
//    public void setContactEmail(String contactEmail) {
//        this.contactEmail = contactEmail;
//    }
//
//    public String getOrderStatusUrl() {
//        return orderStatusUrl;
//    }
//
//    public void setOrderStatusUrl(String orderStatusUrl) {
//        this.orderStatusUrl = orderStatusUrl;
//    }
//
//    public String getPresentmentCurrency() {
//        return presentmentCurrency;
//    }
//
//    public void setPresentmentCurrency(String presentmentCurrency) {
//        this.presentmentCurrency = presentmentCurrency;
//    }
//
//    public TotalLineItemsPriceSet getTotalLineItemsPriceSet() {
//        return totalLineItemsPriceSet;
//    }
//
//    public void setTotalLineItemsPriceSet(TotalLineItemsPriceSet totalLineItemsPriceSet) {
//        this.totalLineItemsPriceSet = totalLineItemsPriceSet;
//    }
//
//    public TotalDiscountsSet getTotalDiscountsSet() {
//        return totalDiscountsSet;
//    }
//
//    public void setTotalDiscountsSet(TotalDiscountsSet totalDiscountsSet) {
//        this.totalDiscountsSet = totalDiscountsSet;
//    }
//
//    public TotalShippingPriceSet getTotalShippingPriceSet() {
//        return totalShippingPriceSet;
//    }
//
//    public void setTotalShippingPriceSet(TotalShippingPriceSet totalShippingPriceSet) {
//        this.totalShippingPriceSet = totalShippingPriceSet;
//    }
//
//    public SubtotalPriceSet getSubtotalPriceSet() {
//        return subtotalPriceSet;
//    }
//
//    public void setSubtotalPriceSet(SubtotalPriceSet subtotalPriceSet) {
//        this.subtotalPriceSet = subtotalPriceSet;
//    }
//
//    public TotalPriceSet getTotalPriceSet() {
//        return totalPriceSet;
//    }
//
//    public void setTotalPriceSet(TotalPriceSet totalPriceSet) {
//        this.totalPriceSet = totalPriceSet;
//    }
//
//    public TotalTaxSet getTotalTaxSet() {
//        return totalTaxSet;
//    }
//
//    public void setTotalTaxSet(TotalTaxSet totalTaxSet) {
//        this.totalTaxSet = totalTaxSet;
//    }
//
//    public List<LineItems> getLineItems() {
//        return lineItems;
//    }
//
//    public void setLineItems(List<LineItems> lineItems) {
//        this.lineItems = lineItems;
//    }
//
//    public List<String> getFulfillments() {
//        return fulfillments;
//    }
//
//    public void setFulfillments(List<String> fulfillments) {
//        this.fulfillments = fulfillments;
//    }
//
//    public List<String> getRefunds() {
//        return refunds;
//    }
//
//    public void setRefunds(List<String> refunds) {
//        this.refunds = refunds;
//    }
//
//    public String getTotalTipReceived() {
//        return totalTipReceived;
//    }
//
//    public void setTotalTipReceived(String totalTipReceived) {
//        this.totalTipReceived = totalTipReceived;
//    }
//
//    public String getOriginalTotalDutiesSet() {
//        return originalTotalDutiesSet;
//    }
//
//    public void setOriginalTotalDutiesSet(String originalTotalDutiesSet) {
//        this.originalTotalDutiesSet = originalTotalDutiesSet;
//    }
//
//    public String getCurrentTotalDutiesSet() {
//        return currentTotalDutiesSet;
//    }
//
//    public void setCurrentTotalDutiesSet(String currentTotalDutiesSet) {
//        this.currentTotalDutiesSet = currentTotalDutiesSet;
//    }
//
//    public String getAdminGraphqlApiId() {
//        return adminGraphqlApiId;
//    }
//
//    public void setAdminGraphqlApiId(String adminGraphqlApiId) {
//        this.adminGraphqlApiId = adminGraphqlApiId;
//    }
////
////    public List<String> getShippingLines() {
////        return shippingLines;
////    }
////
////    public void setShippingLines(List<String> shippingLines) {
////        this.shippingLines = shippingLines;
////    }
//
//    public void setShippingLines(List<ShippingLines> shippingLines) {
//        this.shippingLines = shippingLines;
//    }
//    public List<ShippingLines> getShippingLines() {
//        return shippingLines;
//    }
//
//    public BillingAddress getBillingAddress() {
//        return billingAddress;
//    }
//
//    public void setBillingAddress(BillingAddress billingAddress) {
//        this.billingAddress = billingAddress;
//    }
//
//    public ShippingAddress getShippingAddress() {
//        return shippingAddress;
//    }
//
//    public void setShippingAddress(ShippingAddress shippingAddress) {
//        this.shippingAddress = shippingAddress;
//    }
//
//    public ClientDetails getClientDetails() {
//        return clientDetails;
//    }
//
//    public void setClientDetails(ClientDetails clientDetails) {
//        this.clientDetails = clientDetails;
//    }
//
//    public PaymentDetails getPaymentDetails() {
//        return paymentDetails;
//    }
//
//    public void setPaymentDetails(PaymentDetails paymentDetails) {
//        this.paymentDetails = paymentDetails;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }

}
package org.sir.stripeintegration.infrastructure.service.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sir.stripeintegration.core.application.dtos.customer.request.CustomerCreateRequestDto;
import org.sir.stripeintegration.core.application.dtos.customer.request.CustomerUpdateRequestDto;
import org.sir.stripeintegration.core.application.dtos.customer.response.CustomerDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.request.CreatePaymentMethodRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.response.PaymentMethodDto;
import org.sir.stripeintegration.core.domain.CustomerEntity;
import org.sir.stripeintegration.core.shared.dtoModels.AddressDto;
import org.sir.stripeintegration.core.shared.dtoModels.BillingDetailsDto;
import org.sir.stripeintegration.core.shared.dtoModels.CardDto;
import org.sir.stripeintegration.core.shared.exceptions.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StripeRootService {
    @Value("${stripe.key.public}")
    private String stripePublicKey;

    @Value("${stripe.key.private}")
    private String stripeSecretKey;

    private static final Logger logger = LoggerFactory.getLogger(StripeRootService.class);
    private final ModelMapper mapper = new ModelMapper();

    @PostConstruct
    public void init() {
        Stripe.apiKey = "sk_test_51NCRSGD1Nw0D8DcC8vLiSufo7XBq69Tl1OZcMpMe2MSjyeXchjCW6ZisgKBN0sGbRAD3vOPsYv4SgsT4TBeunfo300hToIi5ZE";
    }

    //region Customer
    public CustomerDto createCustomer(CustomerCreateRequestDto requestDto) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", requestDto.email);
        params.put("name", requestDto.name);
        params.put("phone", requestDto.phone);

        try {
            Customer customer = Customer.create(params);
            return new CustomerDto(customer.getId(), customer.getEmail(), customer.getName(), customer.getPhone());
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to crete customer on stripe");
        }
    }

    public CustomerDto updateCustomer(CustomerUpdateRequestDto requestDto) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", requestDto.email);
        params.put("name", requestDto.name);
        params.put("phone", requestDto.phone);

        try {
            Customer customer = Customer.retrieve(requestDto.customerId);
            Customer updatedCustomer = customer.update(params);

            return new CustomerDto(updatedCustomer.getId(), updatedCustomer.getEmail(),
                    updatedCustomer.getName(), updatedCustomer.getPhone());
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to update customer on stripe");
        }
    }

    public CustomerDto deleteCustomer(String customerId) {
        try {
            Customer customer = Customer.retrieve(customerId);
            Customer deletedCustomer = customer.delete();
            return new CustomerDto(deletedCustomer.getId(), deletedCustomer.getEmail(),
                    deletedCustomer.getName(), deletedCustomer.getPhone());
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when customer try to delete from stripe");
        }
    }
    //endregion

    //region PaymentIntent
    //endregion

    //region PaymentMethod
    public PaymentMethodDto createPaymentMethod(
            CustomerEntity customer, CreatePaymentMethodRequestDto requestDto) {
        Map<String, Object> card = new HashMap<>();
        card.put("number", requestDto.cardNumber);
        card.put("exp_month", requestDto.expMonth);
        card.put("exp_year", requestDto.expYear);
        card.put("cvc", requestDto.cvc);

        Map<String, Object> billingDetails = makeBillingAddressRequestParam(
                customer, requestDto.address);

        Map<String, Object> params = new HashMap<>();
        params.put("type", "card");
        params.put("card", card);
        params.put("billing_details", billingDetails);

        try {
            PaymentMethod paymentMethod = PaymentMethod.create(params);

            Map<String, Object> attachParams = new HashMap<>();
            params.put("customer", customer.customerId);
            paymentMethod = paymentMethod.attach(attachParams);

            return makePaymentMethodResponseDtoFromStripeResponse(paymentMethod);
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to create customer payment method from stripe");
        }
    }

    private Map<String, Object> makeBillingAddressRequestParam(
            CustomerEntity customer, AddressDto addressDto) {
        Map<String, Object> billingDetails = new HashMap<>();
        billingDetails.put("address", makeAddressRequestParam(addressDto));
        billingDetails.put("email", customer.email);
        billingDetails.put("name", customer.name);
        billingDetails.put("phone", customer.phone);

        return billingDetails;
    }

    private Map<String, Object> makeAddressRequestParam(AddressDto addressDto) {
        Map<String, Object> address = new HashMap<>();
        address.put("city", addressDto.city);
        address.put("country", addressDto.country);
        address.put("state", addressDto.state);
        address.put("postal_code", addressDto.postalCode);

        return address;
    }

    private PaymentMethodDto makePaymentMethodResponseDtoFromStripeResponse(
            PaymentMethod paymentMethod){
        PaymentMethodDto paymentMethodDto = new PaymentMethodDto();
        paymentMethodDto.paymentMethodId = paymentMethod.getId();
        paymentMethodDto.customerId = paymentMethod.getCustomer();
        paymentMethodDto.type = paymentMethod.getType();
        paymentMethodDto.billingDetails = mapper.map(
                paymentMethod.getBillingDetails(), BillingDetailsDto.class);
        paymentMethodDto.card = mapper.map(paymentMethod.getCard(), CardDto.class);

        return paymentMethodDto;
    }

    public PaymentMethodDto updatePaymentMethod() {
        return new PaymentMethodDto();
    }

    public PaymentMethodDto deletePaymentMethod() {
        return new PaymentMethodDto();
    }

    public List<PaymentMethodDto> getCustomerAllPaymentMethods(
            Integer limit, String startingAfter, String endingBefore
    ) {
        return new ArrayList<>();
    }

    public PaymentMethodDto getCustomerPaymentMethodById() {
        return new PaymentMethodDto();
    }
    //endregion

    //region Product
    //endregion

    //region ProductPrice
    //endregion

    //region Subscription
    //endregion
}

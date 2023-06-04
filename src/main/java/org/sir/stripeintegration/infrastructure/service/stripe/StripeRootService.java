package org.sir.stripeintegration.infrastructure.service.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.sir.stripeintegration.core.application.dtos.request.CustomerCreateRequestDto;
import org.sir.stripeintegration.core.application.dtos.request.CustomerUpdateRequestDto;
import org.sir.stripeintegration.core.application.dtos.response.CustomerDto;
import org.sir.stripeintegration.core.shared.exceptions.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StripeRootService {
    @Value("${stripe.key.public}")
    private String stripePublicKey;

    @Value("${stripe.key.private}")
    private String stripeSecretKey;

    private static final Logger logger = LoggerFactory.getLogger(StripeRootService.class);

    @PostConstruct
    public void init() {
        Stripe.apiKey = "sk_test_51NCRSGD1Nw0D8DcC8vLiSufo7XBq69Tl1OZcMpMe2MSjyeXchjCW6ZisgKBN0sGbRAD3vOPsYv4SgsT4TBeunfo300hToIi5ZE";
    }

    //region Customer
    public CustomerDto createCustomer(CustomerCreateRequestDto requestDto){
        Map<String, Object> params = new HashMap<>();
        params.put("email", requestDto.email);
        params.put("name", requestDto.name);
        params.put("phone", requestDto.phone);

        try {
            Customer customer = Customer.create(params);
            return new CustomerDto(customer.getId(), customer.getEmail(), customer.getName(), customer.getPhone());
        }
        catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to crete customer on stripe");
        }
    }

    public CustomerDto updateCustomer(CustomerUpdateRequestDto requestDto){
        Map<String, Object> params = new HashMap<>();
        params.put("email", requestDto.email);
        params.put("name", requestDto.name);
        params.put("phone", requestDto.phone);

        try {
            Customer customer = Customer.retrieve(requestDto.customerId);
            Customer updatedCustomer = customer.update(params);

            return new CustomerDto(updatedCustomer.getId(), updatedCustomer.getEmail(),
                    updatedCustomer.getName(), updatedCustomer.getPhone());
        }
        catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to update customer on stripe");
        }
    }

    public CustomerDto deleteCustomer(String customerId){
        try {
            Customer customer = Customer.retrieve(customerId);
            Customer deletedCustomer = customer.delete();
            return new CustomerDto(deletedCustomer.getId(), deletedCustomer.getEmail(),
                    deletedCustomer.getName(), deletedCustomer.getPhone());
        }
        catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when customer try to delete from stripe");
        }
    }
    //endregion

    //region PaymentIntent
    //endregion

    //region PaymentMethod
    //endregion

    //region Product
    //endregion

    //region ProductPrice
    //endregion

    //region Subscription
    //endregion
}

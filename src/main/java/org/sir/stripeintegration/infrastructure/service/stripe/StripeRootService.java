package org.sir.stripeintegration.infrastructure.service.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sir.stripeintegration.core.application.dtos.customer.request.CustomerCreateRequestDto;
import org.sir.stripeintegration.core.application.dtos.customer.request.CustomerUpdateRequestDto;
import org.sir.stripeintegration.core.application.dtos.customer.response.CustomerDto;
import org.sir.stripeintegration.core.application.dtos.paymentIntent.request.CreatePaymentIntentRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentIntent.response.PaymentIntentDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.request.CreatePaymentMethodRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.request.UpdatePaymentMethodRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.response.PaymentMethodDto;
import org.sir.stripeintegration.core.application.dtos.product.request.CreateProductRequestDto;
import org.sir.stripeintegration.core.application.dtos.product.request.UpdateProductRequestDto;
import org.sir.stripeintegration.core.application.dtos.product.response.ProductDto;
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
        Stripe.apiKey = "sk_test_51NCRSGD1Nw0D8DcC8vLiSufo7XBq69Tl1OZcMpMe2MSjyeXchjCW6ZisgKBN0sGbRAD3vOPsYv4SgsT4TB" +
                "eunfo300hToIi5ZE";
    }

    //region Customer
    public CustomerDto createCustomer(CustomerCreateRequestDto requestDto) {
        CustomerCreateParams params = CustomerCreateParams.builder()
                .setEmail(requestDto.email)
                .setName(requestDto.name)
                .setPhone(requestDto.phone)
                .build();

        try {
            Customer customer = Customer.create(params);
            return getCustomerDtoFromCustomerObject(customer);
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to create customer on stripe");
        }
    }

    public List<CustomerDto> getAllCustomers(Long limit, String startingAfter, String endingBefore) {
        try {
            CustomerListParams params = CustomerListParams.builder()
                    .setLimit(limit)
                    .setStartingAfter(startingAfter)
                    .setEndingBefore(endingBefore)
                    .build();

            CustomerCollection customers = Customer.list(params);

            List<CustomerDto> customerDtos = new ArrayList<>();
            customers.getData().forEach(customer -> customerDtos.add(getCustomerDtoFromCustomerObject(customer)));

            return customerDtos;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to get customer list on stripe");
        }
    }

    private CustomerDto getCustomerDtoFromCustomerObject(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .name(customer.getName())
                .phone(customer.getPhone())
                .build();
    }

    public CustomerDto updateCustomer(CustomerUpdateRequestDto requestDto) {
        CustomerUpdateParams params = CustomerUpdateParams.builder()
                .setEmail(requestDto.email)
                .setName(requestDto.name)
                .setPhone(requestDto.phone)
                .build();

        try {
            Customer customer = Customer.retrieve(requestDto.id);
            customer = customer.update(params);

            return getCustomerDtoFromCustomerObject(customer);
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to update customer on stripe");
        }
    }

    public void deleteCustomer(String customerId) {
        try {
            Customer customer = Customer.retrieve(customerId);
            customer.delete();
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when customer try to delete from stripe");
        }
    }
    //endregion

    //region PaymentIntent
    public PaymentIntentDto createPaymentIntent(CreatePaymentIntentRequestDto requestDto) {
        try {
            Customer customer = Customer.retrieve(requestDto.customerId);

            if (customer.getInvoiceSettings().getDefaultPaymentMethod() == null) {
                throw new CustomException("Customer default payment method not set yet");
            }

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(requestDto.amount.longValue())
                    .setCurrency(requestDto.currency)
                    .setConfirm(true)
                    .setCustomer(requestDto.customerId)
                    .setPaymentMethod(customer.getInvoiceSettings().getDefaultPaymentMethod())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            return getPaymentIntentDtoFromPaymentIntentObject(paymentIntent);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to create payment intent on stripe");
        }
    }

    private PaymentIntentDto getPaymentIntentDtoFromPaymentIntentObject(PaymentIntent paymentIntent) {
        return PaymentIntentDto.builder()
                .id(paymentIntent.getId())
                .amount(paymentIntent.getAmount().intValue())
                .status(paymentIntent.getStatus())
                .currency(paymentIntent.getCurrency())
                .customerId(paymentIntent.getCustomer())
                .paymentMethodId(paymentIntent.getPaymentMethod())
                .build();
    }

    public PaymentIntentDto getCustomerPaymentIntentById(String id, String customerId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(id);

            if (!paymentIntent.getCustomer().equals(customerId)) {
                throw new CustomException("This payment intent is not belongs to this customer");
            }

            return getPaymentIntentDtoFromPaymentIntentObject(paymentIntent);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to retrieve customer payment method on stripe");
        }
    }

    public List<PaymentIntentDto> getCustomerAllPaymentIntents(
            String customerId, Long limit, String startingAfter, String endingBefore) {
        try {
            PaymentIntentListParams params = PaymentIntentListParams.builder()
                    .setCustomer(customerId)
                    .setLimit(limit)
                    .setStartingAfter(startingAfter)
                    .setEndingBefore(endingBefore)
                    .build();

            PaymentIntentCollection paymentIntents = PaymentIntent.list(params);

            List<PaymentIntentDto> paymentIntentDtos = new ArrayList<>();
            paymentIntents.getData().forEach(paymentIntent ->
                    paymentIntentDtos.add(getPaymentIntentDtoFromPaymentIntentObject(paymentIntent)));

            return paymentIntentDtos;
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to retrieve customer payment intents on stripe");
        }
    }
    //endregion

    //region PaymentMethod
    public PaymentMethodDto createPaymentMethod(CustomerEntity customer, CreatePaymentMethodRequestDto requestDto) {
        Map<String, Object> card = new HashMap<>();
        card.put("number", requestDto.cardNumber);
        card.put("exp_month", requestDto.expMonth);
        card.put("exp_year", requestDto.expYear);
        card.put("cvc", requestDto.cvc);

        Map<String, Object> billingDetails = makeBillingAddressRequestParam(customer, requestDto.address);

        Map<String, Object> params = new HashMap<>();
        params.put("type", "card");
        params.put("card", card);
        params.put("billing_details", billingDetails);

        try {
            PaymentMethod paymentMethod = PaymentMethod.create(params);

            Map<String, Object> attachParams = new HashMap<>();
            attachParams.put("customer", customer.id);
            paymentMethod = paymentMethod.attach(attachParams);

            return makePaymentMethodResponseDtoFromStripeResponse(paymentMethod);
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to create customer payment method on stripe");
        }
    }

    private Map<String, Object> makeBillingAddressRequestParam(CustomerEntity customer, AddressDto addressDto) {
        Map<String, Object> billingDetails = new HashMap<>();
        billingDetails.put("address", makeAddressRequestParam(addressDto));
        billingDetails.put("email", customer.email);
        billingDetails.put("name", customer.name);
        billingDetails.put("phone", customer.phone);

        return billingDetails;
    }

    private Map<String, Object> makeAddressRequestParam(AddressDto addressDto) {
        Map<String, Object> address = new HashMap<>();

        if (addressDto != null) {
            address.put("city", addressDto.city);
            address.put("country", addressDto.country);
            address.put("state", addressDto.state);
            address.put("postal_code", addressDto.postalCode);
        }

        return address;
    }

    private PaymentMethodDto makePaymentMethodResponseDtoFromStripeResponse(PaymentMethod paymentMethod) {
        return PaymentMethodDto.builder()
                .id(paymentMethod.getId())
                .customerId(paymentMethod.getCustomer())
                .type(paymentMethod.getType())
                .billingDetails(mapper.map(paymentMethod.getBillingDetails(), BillingDetailsDto.class))
                .card(mapper.map(paymentMethod.getCard(), CardDto.class))
                .build();
    }

    public PaymentMethodDto updatePaymentMethod(UpdatePaymentMethodRequestDto requestDto) {
        Map<String, Object> address = makeAddressRequestParam(requestDto.address);
        Map<String, Object> billingDetails = new HashMap<>();
        billingDetails.put("address", address);

        Map<String, Object> card = new HashMap<>();
        card.put("exp_month", requestDto.expMonth);
        card.put("exp_year", requestDto.expYear);

        Map<String, Object> params = new HashMap<>();
        params.put("card", card);
        params.put("billing_details", billingDetails);

        try {
            PaymentMethod paymentMethod = PaymentMethod.retrieve(requestDto.id);
            paymentMethod = paymentMethod.update(params);
            return makePaymentMethodResponseDtoFromStripeResponse(paymentMethod);
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to update customer payment method on stripe");
        }
    }

    public void deletePaymentMethod(String id) {
        try {
            PaymentMethod paymentMethod = PaymentMethod.retrieve(id);
            paymentMethod.detach();
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to detach customer payment method on stripe");
        }
    }

    public List<PaymentMethodDto> getCustomerAllPaymentMethods(
            String customerId, Long limit, String startingAfter, String endingBefore) {
        try {
            Customer customer = Customer.retrieve(customerId);

            CustomerListPaymentMethodsParams params = CustomerListPaymentMethodsParams.builder()
                    .setType(CustomerListPaymentMethodsParams.Type.CARD)
                    .setLimit(limit)
                    .setStartingAfter(startingAfter)
                    .setEndingBefore(endingBefore)
                    .build();

            PaymentMethodCollection paymentMethods = customer.listPaymentMethods(params);

            List<PaymentMethodDto> paymentMethodDtos = new ArrayList<>();
            paymentMethods.getData().forEach(paymentMethod ->
                    paymentMethodDtos.add(makePaymentMethodResponseDtoFromStripeResponse(paymentMethod)));

            return paymentMethodDtos;
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to retrieve customer payment methods on stripe");
        }
    }

    public PaymentMethodDto getCustomerPaymentMethodById(String id, String customerId) {
        try {
            PaymentMethod paymentMethod = PaymentMethod.retrieve(id);

            if (!paymentMethod.getCustomer().equals(customerId)) {
                throw new CustomException("This payment method is not belongs to this customer");
            }

            return makePaymentMethodResponseDtoFromStripeResponse(paymentMethod);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to retrieve customer payment method on stripe");
        }
    }

    public PaymentMethodDto setCustomerDefaultPaymentMethod(String customerId, String paymentMethodId) {
        Map<String, Object> invoiceSettings = new HashMap<>();
        invoiceSettings.put("default_payment_method", paymentMethodId);

        Map<String, Object> params = new HashMap<>();
        params.put("invoice_settings", invoiceSettings);

        try {
            PaymentMethodDto paymentMethodDto = getCustomerPaymentMethodById(paymentMethodId, customerId);

            Customer customer = Customer.retrieve(customerId);
            customer.update(params);

            return paymentMethodDto;
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to update customer default payment method on stripe");
        }
    }
    //endregion

    //region Product
    public ProductDto createProduct(CreateProductRequestDto requestDto) {
        ProductCreateParams params = ProductCreateParams.builder()
                .setName(requestDto.name)
                .setDescription(requestDto.description)
                .setActive(requestDto.active)
                .setShippable(requestDto.shippable)
                .addAllImage(requestDto.images)
                .build();

        try {
            Product product = Product.create(params);

            return getProductDtoFromProductObject(product);
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to create product on stripe");
        }
    }

    private ProductDto getProductDtoFromProductObject(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .defaultPriceId(product.getDefaultPrice())
                .name(product.getName())
                .active(product.getActive())
                .shippable(product.getShippable())
                .description(product.getDescription())
                .images(product.getImages())
                .build();
    }

    public ProductDto updateProduct(UpdateProductRequestDto requestDto) {
        ProductUpdateParams params = ProductUpdateParams.builder()
                .setActive(requestDto.active)
                .setName(requestDto.name)
                .setDescription(requestDto.description)
                .setShippable(requestDto.shippable)
                .setImages(requestDto.images)
                .setDefaultPrice(requestDto.defaultPriceId)
                .build();

        try {
            Product product = Product.retrieve(requestDto.id);
            product = product.update(params);

            return getProductDtoFromProductObject(product);
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to update product on stripe");
        }
    }

    public ProductDto getProductById(String id) {
        try {
            Product product = Product.retrieve(id);
            return getProductDtoFromProductObject(product);
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to get product on stripe");
        }
    }

    public List<ProductDto> getAllProducts(
            Boolean active, Boolean shippable, Long limit, String startingAfter, String endingBefore) {
        ProductListParams params = ProductListParams.builder()
                .setActive(active)
                .setShippable(shippable)
                .setLimit(limit)
                .setStartingAfter(startingAfter)
                .setEndingBefore(endingBefore)
                .build();

        try {
            ProductCollection products = Product.list(params);
            List<ProductDto> productDtos = new ArrayList<>();

            products.getData().forEach(product -> productDtos.add(getProductDtoFromProductObject(product)));
            return productDtos;
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when try to get products on stripe");
        }
    }

    public void deleteProductById(String id) {
        try {
            Product product = Product.retrieve(id);
            product.delete();
        } catch (StripeException e) {
            logger.error(e.getMessage());
            throw new CustomException("Error when product try to delete from stripe");
        }
    }
    //endregion

    //region ProductPrice
    //endregion

    //region Subscription
    //endregion
}

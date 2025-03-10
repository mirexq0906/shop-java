package com.example.shop_java.command;

import com.example.shop_java.entity.Category;
import com.example.shop_java.entity.Product;
import com.example.shop_java.entity.order.DeliveryType;
import com.example.shop_java.entity.order.PaymentMethod;
import com.example.shop_java.entity.user.Role;
import com.example.shop_java.entity.user.User;
import com.example.shop_java.repository.CategoryRepository;
import com.example.shop_java.repository.ProductRepository;
import com.example.shop_java.repository.UserRepository;
import com.example.shop_java.repository.order.DeliveryTypeRepository;
import com.example.shop_java.repository.order.PaymentMethodRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class DatabaseCommand {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final DeliveryTypeRepository deliveryTypeRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final Random random = new Random();

    @ShellMethod(key = "database-refresh")
    public void run() {
        this.userRepository.deleteAll();
        this.categoryRepository.deleteAll();
        this.productRepository.deleteAll();
        this.deliveryTypeRepository.deleteAll();
        this.paymentMethodRepository.deleteAll();

        List<User> users = this.getUsers();
        List<Category> categories = this.getCategories();
        List<Product> products = this.getProducts(categories);
        List<DeliveryType> deliveryTypes = this.getDeliveryTypes();
        List<PaymentMethod> paymentMethods = this.getPaymentMethods();

        this.userRepository.saveAll(users);
        this.categoryRepository.saveAll(categories);
        this.productRepository.saveAll(products);
        this.deliveryTypeRepository.saveAll(deliveryTypes);
        this.paymentMethodRepository.saveAll(paymentMethods);
    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setUsername("test" + i);
            user.setEmail("test" + i + "@mail.com");
            user.setRole(Role.ROLE_USER);
            user.setPassword(passwordEncoder.encode("123456"));

            users.add(user);
        }

        return users;
    }

    private List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Category category = new Category();
            category.setName("category " + i);
            categories.add(category);
        }

        return categories;
    }

    private List<Product> getProducts(List<Category> categories) {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Product product = new Product();
            product.setName("product " + i);
            product.setDescription("description " + i);
            product.setPrice((long) (100 + (i + 1)));
            product.setOldPrice((long) (200 + (i + 1)));
            product.setImage("image" + i + ".jpg");
            product.setCategory(categories.get(random.nextInt(categories.size())));
            try {
                product.setGallery(
                        objectMapper.writeValueAsString(
                                List.of(
                                        "gallery" + i + ".jpg"
                                )
                        )
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            products.add(product);
        }

        return products;
    }

    private List<DeliveryType> getDeliveryTypes() {
        List<DeliveryType> deliveryTypes = new ArrayList<>();

        DeliveryType deliveryType1 = new DeliveryType();
        deliveryType1.setName("Доставка");
        deliveryType1.setPrice(500L);
        deliveryType1.setWithAddress(Boolean.TRUE);

        DeliveryType deliveryType2 = new DeliveryType();
        deliveryType2.setName("Самовывоз");
        deliveryType2.setPrice(0L);
        deliveryType2.setWithAddress(Boolean.FALSE);

        deliveryTypes.add(deliveryType1);
        deliveryTypes.add(deliveryType2);
        return deliveryTypes;
    }

    private List<PaymentMethod> getPaymentMethods() {
        List<PaymentMethod> paymentMethods = new ArrayList<>();

        PaymentMethod paymentMethod1 = new PaymentMethod();
        paymentMethod1.setName("Наличными");
        paymentMethod1.setRedirectToPay(Boolean.FALSE);

        PaymentMethod paymentMethod2 = new PaymentMethod();
        paymentMethod2.setName("Картой");
        paymentMethod2.setRedirectToPay(Boolean.TRUE);

        paymentMethods.add(paymentMethod1);
        paymentMethods.add(paymentMethod2);
        return paymentMethods;
    }

}

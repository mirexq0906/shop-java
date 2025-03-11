package com.example.shop_java.web.controller;

import com.example.shop_java.entity.Category;
import com.example.shop_java.entity.Product;
import com.example.shop_java.entity.Seo;
import com.example.shop_java.entity.order.*;
import com.example.shop_java.entity.order.Order;
import com.example.shop_java.entity.user.Role;
import com.example.shop_java.entity.user.User;
import com.example.shop_java.repository.*;
import com.example.shop_java.repository.order.*;
import com.example.shop_java.service.*;
import com.example.shop_java.service.user.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractControllerTest {

    private final Random random = new Random();

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected AuthService authService;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CategoryService categoryService;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected CartRepository cartRepository;

    @Autowired
    protected CartItemRepository cartItemRepository;

    @Autowired
    protected SeoService seoService;

    @Autowired
    protected SeoRepository seoRepository;

    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected OrderItemRepository orderItemRepository;

    @Autowired
    protected DeliveryTypeRepository deliveryTypeRepository;

    @Autowired
    protected PaymentMethodRepository paymentMethodRepository;

    @Autowired
    protected OrderCustomerRepository orderCustomerRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected RedisTemplate redisTemplate;

    protected static PostgreSQLContainer<?> postgreSQLContainer;

    protected static RedisContainer redisContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:12.3"));
        redisContainer = new RedisContainer(DockerImageName.parse("redis:7.0.12"))
                .withExposedPorts(6379).withReuse(true);
        postgreSQLContainer.start();
        redisContainer.start();
    }

    @DynamicPropertySource
    public static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379).toString());
    }

    @BeforeAll
    public void setUpAll() {
        DeliveryType deliveryType = new DeliveryType();
        deliveryType.setName("delivery");
        deliveryType.setPrice(100L);
        deliveryType.setWithAddress(Boolean.TRUE);
        deliveryTypeRepository.save(deliveryType);

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setName("payment");
        paymentMethod.setRedirectToPay(Boolean.TRUE);
        paymentMethodRepository.save(paymentMethod);
    }

    @BeforeEach
    void setUp() {
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
        List<Category> categories = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        User user = new User();
        user.setUsername("test");
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("12345"));
        user.setRole(Role.ROLE_USER);
        authService.register(user);

        for (int i = 0; i < 10; i++) {
            Category category = new Category();
            category.setName("test " + (i + 1));
            categories.add(category);
            categoryService.save(category);
        }

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setName("product " + (i + 1));
            product.setDescription("description " + (i + 1));
            product.setPrice((long) (100 + (i + 1)));
            product.setOldPrice((long) (200 + (i + 1)));
            product.setImage("image" + (i + 1) + ".png");
            product.setCategory(categories.get(random.nextInt(categories.size())));
            try {
                product.setGallery(objectMapper.writeValueAsString(List.of("gallery1.png")));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            productService.save(product);
            products.add(product);
        }

        for (int i = 0; i < 10; i++) {
            Seo seo = new Seo();
            seo.setUri("uri/" + (i + 1));
            seo.setTitle("title " + (i + 1));
            seo.setDescription("description " + (i + 1));
            seo.setKeywords("keywords " + (i + 1));

            this.seoService.save(seo);
        }

        OrderCustomer orderCustomer = new OrderCustomer();
        orderCustomer.setEmail(user.getEmail());
        orderCustomer.setFirstName(user.getUsername());
        orderCustomer.setLastName(user.getUsername());
        orderCustomer.setAddress("test address");
        orderCustomer.setPhone("79999999999");

        Order order = new Order();
        order.setStatus(StatusOrder.PENDING);
        order.setAmount(203L);
        order.setOrderItems(
                products.stream().map((item) -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(item);
                    orderItem.setPrice(item.getPrice());
                    orderItem.setQuantity(1);
                    orderItem.setOrder(order);
                    return orderItem;
                }).limit(2L).toList()
        );
        order.setUser(user);
        order.setCustomer(orderCustomer);
        DeliveryType deliveryType = deliveryTypeRepository.findById(1L).orElse(null);
        PaymentMethod paymentMethod = paymentMethodRepository.findById(1L).orElse(null);
        order.setDeliveryType(deliveryType);
        order.setPaymentMethod(paymentMethod);

        orderRepository.save(order);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        productRepository.deleteAll();
        cartRepository.deleteAll();
        cartItemRepository.deleteAll();
        seoRepository.deleteAll();
        orderRepository.deleteAll();
        orderCustomerRepository.deleteAll();
        orderItemRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE users_id_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE categories_id_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE products_id_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE carts_id_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE cart_items_id_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE seos_id_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE orders_id_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE order_customers_id_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE order_items_id_seq RESTART WITH 1");

    }

    @AfterAll
    void tearDownAll() {
        deliveryTypeRepository.deleteAll();
        paymentMethodRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE payment_methods_id_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE delivery_types_id_seq RESTART WITH 1");
    }

}

package com.pavel.spring.messaging;


//@RequiredArgsConstructor
//@Component
//
//public class Producer {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
//
//    @EventListener(ApplicationStartedEvent.class)
//    public void generate() {
//        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
//
//        AtomicInteger counter = new AtomicInteger(0);
//        scheduledExecutor.scheduleAtFixedRate(() -> {
//            var msg = faker.beer().name();
//            logger.info("Sending message to Kafka: " + msg);
//            kafkaTemplate.send("beers", String.valueOf(counter.getAndIncrement()), faker.beer().name());
//
//        }, 0, 1, TimeUnit.SECONDS);
//    }
//}

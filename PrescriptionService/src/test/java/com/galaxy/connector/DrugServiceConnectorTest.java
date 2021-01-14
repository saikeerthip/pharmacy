package com.galaxy.connector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
@SpringBootTest
public class DrugServiceConnectorTest {

    @Autowired
    private DrugServiceConnector drugServiceConnector;

    @Test
    public void testRetry() throws Exception {
        try {
            drugServiceConnector.isDrugValid("MRNA");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Test
    public void isDrugValid() {
    }
    @Mock
    private DrugServiceConnector drugServiceConnector;

    @Test
    public void testRetry() throws Exception {
        Boolean response = this.drugServiceConnector.isDrugValid("rna");
        verify(drugServiceConnector, times(3)).isDrugValid("rna");
        //assertThat(response, is("Completed"));
    }

    @Configuration
    @EnableAspectJAutoProxy
    public static class SpringRetryAspectConfig {

        @Bean
        public DrugServiceConnector drugServiceConnector() throws Exception {
            DrugServiceConnector remoteService = mock(DrugServiceConnector.class);
            when(remoteService.isDrugValid("rna"))
                    .thenThrow(new ResourceAccessException("Remote Exception 1"));
            return remoteService;
        }

        *//*@Bean
        public RetryAspect retryAspect() {
            return new RetryAspect();
        }*//*

        @Bean
        public RetryTemplate retryTemplate() {
            RetryTemplate retryTemplate = new RetryTemplate();
            FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
            fixedBackOffPolicy.setBackOffPeriod(2000l);
            retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

            SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
            retryPolicy.setMaxAttempts(3);

            retryTemplate.setRetryPolicy(retryPolicy);
            return retryTemplate;
        }

        @Bean
        public RestTemplate restTemplate(RestTemplateBuilder builder) {

            return builder.setConnectTimeout(Duration.ofMillis(300000))
                    .setReadTimeout(Duration.ofMillis(300000)).build();

		*//*return builder.setConnectTimeout(30000)
				.setReadTimeout(30000).build();*//*
        }
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {

            return new RestTemplateBuilder();
        }
    }*/
}
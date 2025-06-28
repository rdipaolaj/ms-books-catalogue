package com.unir.missiact1.msbookscatalogue.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {
  
  @Bean
  public GroupedOpenApi apiV1() {
    return GroupedOpenApi.builder()
      .group("v1")
      .pathsToMatch("/v1/api/**", "/health")
      .build();
  }
  
  @Bean
  public GroupedOpenApi apiV2() {
    return GroupedOpenApi.builder()
      .group("v2")
      .pathsToMatch("/v2/api/**")
      .build();
  }
}
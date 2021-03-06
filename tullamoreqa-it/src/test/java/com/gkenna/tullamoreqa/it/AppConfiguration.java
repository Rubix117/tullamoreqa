/*
 * Copyright (c) 2018. Gavin Kenna
 */

package com.gkenna.tullamoreqa.it;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"com.gkenna.tullamoreqa.*"})
@EnableTransactionManagement
@EnableJpaRepositories("com.gkenna.tullamoreqa.core.api.repositories")
public class AppConfiguration {
}

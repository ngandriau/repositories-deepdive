/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.app;

import org.app.config.DBConfig;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;


/**
 * Sample test case bootstrapping the application.
 */
public class ApplicationBootstrappingTest
{

	@Test
	public void bootstrapsApplication() {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext(DBConfig.class);
        Environment env = appCtx.getEnvironment();

        assertThat(env.getProperty("project.name"), is("nico-repositories-deeplive"));
	}
}

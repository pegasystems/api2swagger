package com.pega.api2swagger;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.pega.api2swagger.schemagen.TestSchemaGeneration;
import com.pega.api2swagger.utils.TestURLUtils;

@RunWith(Suite.class)
@SuiteClasses({TestURLUtils.class, TestSchemaGeneration.class})
public class SwaggerGeneratorTestSuite {

}

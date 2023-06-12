package com.example.demo;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.annotation.AuditLog;
import com.example.demo.bean.ClassIntrospectResult;
import com.example.demo.bean.CustomGreeting;
import com.example.demo.bean.ExampleBean1;
import com.example.demo.bean.ExampleBean2;
import com.example.demo.bean.Greeting;
import com.example.demo.bean.InnerBean;
import com.example.demo.bean.InnerBeanA;
import com.example.demo.bean.MyContent;
import com.example.demo.proxy.DynamicInvocationHandler;
import com.example.demo.proxy.GreetingMethods;
import com.example.demo.proxy.MyGreeting;
import com.example.demo.reflectUtils.MyReflectUtil;
import com.example.demo.serialization.Production;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import lombok.extern.slf4j.Slf4j;
@RestController
@Slf4j
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	ExampleBean1 bean1;

	@Autowired
	ExampleBean2 bean2;

	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/greeting")
	@AuditLog(module="GREETING")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) throws Exception {
		log.info("You are greeting");
		try {
			Class clz = Class.forName("com.example.demo.reflect.MyReflect");
			Constructor[] constructors = clz.getConstructors();
			Object instance= constructors[0].newInstance();

			Method setMethod = clz.getMethod("setContext",String.class);
			setMethod.invoke(instance,name);

			Method getMethod = clz.getMethod("getContext");
			String contextAfter = (String)getMethod.invoke(instance);
			log.info("Context after reflect:{}",contextAfter);
			
			Field myField = clz.getDeclaredField("myField");
			Object val = myField.get(instance);
			log.info("MyField:{}", val);
			return new Greeting(counter.incrementAndGet(), String.format(template, contextAfter));
		} catch (Exception  e) {
			throw e;
		} 
		
		
	}


		/**
	 * @param name
	 * @return
	 */
	@GetMapping("/mygreeting")
	public CustomGreeting<MyContent> mygreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		log.info("You are greeting with special");
		MyContent content = new MyContent(name);
		return new CustomGreeting<MyContent>(counter.incrementAndGet(), content);
	}


	@GetMapping("/reflect/show")
	public List<ClassIntrospectResult> show() {
		List<ClassIntrospectResult> result = new ArrayList<ClassIntrospectResult>(3);
		String[] clzzs = new String[]{
			"com.example.demo.bean.InnerBeanA",
			"com.example.demo.bean.InnerBeanB",
			"com.example.demo.bean.InnerBeanC",
			"com.example.demo.bean.InnerBeanD",
			"gx.spring.mybeans.BeanA",
			"gx.spring.mybeans.BeanB",
			"gx.spring.mybeans.BeanC",
			"gx.spring.mybeans.BeanD"
		};
		for (String clzStr : clzzs) {
			try {
				log.info("Reflect for class:{}",clzStr);
				result.add(MyReflectUtil.introspectFieldsAndMethodsFrom(clzStr));
			} catch (Exception e) {
				log.error("Failed", e);
			}
		}

		return result;
	}

	@GetMapping("/reflect/execute")
	public String execute(@RequestParam(value = "method") String method,@RequestParam(value = "clazz") String clazz){
		Class clz = null ;
		try {
			clz = Class.forName(clazz);
		} catch (Exception e) {
			log.warn("Class not found",e);
			return "Class not found";
		}
		Constructor[] constructors = clz.getDeclaredConstructors();
		if(constructors.length == 0){
			return "No constructor method";
		}
		Object instance;
		try {
			instance = constructors[0].newInstance();
		} catch (Exception e) {
			log.warn("Failed to instance class user constructor:",e);
			return "Failed to instance class user constructor:"+e.getMessage();
		}

		Method m = null;
		try {
			m = clz.getDeclaredMethod(method);
		} catch (Exception e) {
			log.warn("Failed to get method",e);
			return "Failed to get method "+ method;
		}
		if (m == null) {
			return "No method found";
		}

		Object rst = null;
		try {
			rst = m.invoke(instance);
		} catch (Exception e) {
			log.warn("Failed to execute method",e);
			return "Failed to execute method";
		}
		return "Execute suc, result:"+rst;
	}

	@GetMapping("/proxy")
	public String execute(){
		GreetingMethods greeting = (GreetingMethods)DynamicInvocationHandler.newInstance(new MyGreeting());
		return greeting.sayHello();
	}

	@GetMapping("/printBeans")
	public String printbeans(){
		StringBuilder sb = new StringBuilder("");
		sb.append(bean1).append("\\n");
		sb.append(bean2);
		return sb.toString();
	}

	@GetMapping("/serialization")
	public String serialization(){
		Production prod = new Production();
		prod.setId(1);
		prod.setPrice(1.2d);
		prod.setName("book");
		prod.setDescription("this is  a book");
		byte[] serial = SerializationUtils.serialize(prod);
		SerializationUtils.deserialize(serial);
		return "OK";
	}

	@GetMapping("/file")
	public String file(@RequestParam(value = "fileName") String filename ){
		StringBuilder sb = new StringBuilder();
		try (Reader reader = new BufferedReader(new InputStreamReader(
				ClassLoader.getSystemResourceAsStream(filename), StandardCharsets.UTF_8))) {
			int c = 0;
			while ((c = reader.read()) != -1) {
				sb.append((char) c);
			} 
		}catch (Exception e) {
			sb.append(e.getMessage());
		}
		return sb.toString();
	}
}

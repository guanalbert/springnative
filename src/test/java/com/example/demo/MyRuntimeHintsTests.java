package com.example.demo;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.predicate.RuntimeHintsPredicates;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;

// import com.example.demo.reflect.MyReflect;
// import com.example.demo.reflect.MyReflectRuntimeHint;
//import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MyRuntimeHintsTests {

    @Test
    void shouldRegisterHints() {
        // RuntimeHints hints = new RuntimeHints();
        // new MyReflectRuntimeHint().registerHints(hints, getClass().getClassLoader());
        // Method method = ReflectionUtils.findMethod(MyReflect.class, "doSomething");
        // assertThat(RuntimeHintsPredicates.reflection().onMethod(method).invoke()).accepts(hints);

    }
}

package org.codehaus.mojo.modernjava;
import java.util.List;

public class LambdaSample {
    public void executeModernCode() {
        List<String> items = List.of("Eduardo", "MojoHaus", "Java21");
        items.forEach(item -> System.out.println("Processing: " + item));
    }
}

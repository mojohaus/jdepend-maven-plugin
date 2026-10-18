package org.codehaus.mojo.modernjava;
import java.util.List;
import java.util.ArrayList;
import org.codehaus.mojo.modernjava.another.Another;

public class LambdaSample {

    private List<RecordSample> records = new ArrayList<>();

    public void executeModernCodeRecordSample() {
        records.add(new RecordSample("1", "ACTIVE"));
        records.forEach(record -> System.out.println(record.getFormattedStatus()));
    }

    public void executeModernCode() {
        Another another = new Another();
        List<String> items = List.of("Eduardo", "MojoHaus", "Java21", another.hey());
        items.forEach(item -> System.out.println("Processing: " + item));
    }

    public void executeModernCode2() {
        Another another = new Another();
        List<String> items = List.of("Eduardo", "MojoHaus", "Java21", another.hey());

        items.forEach(item -> System.out.println("Processing: " + item + " with " + another.toString()));
    }
}

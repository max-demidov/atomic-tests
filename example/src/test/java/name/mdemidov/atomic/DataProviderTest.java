package name.mdemidov.atomic;

import lombok.val;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataProviderTest {

    static final List<String> LIST1 = new ArrayList<>(); // some list of test data
    static final List<Map<String, String>> LIST2 = new ArrayList<>(); // another list of test data

    @DataProvider
    Iterator<Object> example1() {
        return LIST1.stream().map(i -> (Object) i).iterator();
    }

    @DataProvider
    Iterator<Object[]> example2() {
        return LIST2.stream().map(i -> new Object[]{i.get("key1"), i.get("key2")}).iterator();
    }

    @DataProvider
    Iterator<Object[]> combination() {
        val set = new HashSet<Object[]>();
        LIST1.forEach(
            i1 -> LIST2.forEach(
                i2 -> set.add(new Object[]{i1, i2})
            )
        );
        return set.iterator();
    }
}

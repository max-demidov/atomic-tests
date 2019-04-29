package name.mdemidov.atomic;

import static org.hamcrest.Matchers.not;

import lombok.val;
import name.mdemidov.atomic.basetest.AbstractTest;
import name.mdemidov.atomic.example.service.some.SomeService;
import org.testng.annotations.Test;

import java.io.IOException;

public class ServiceTest extends AbstractTest {

    @Test(description = "Check that some service returns non zero price")
    public void checkThatSomeServiceReturnsNonZeroPrice() throws IOException {
        val service = new SomeService();
        val price = service.getSomeData().getPrice();
        assertThat("Price:", price, not(0.0));
    }
}

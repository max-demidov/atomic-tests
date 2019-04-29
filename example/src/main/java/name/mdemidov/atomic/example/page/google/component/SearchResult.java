package name.mdemidov.atomic.example.page.google.component;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TextBlock;

public class SearchResult extends HtmlElement {

    @FindBy(tagName = "h3")
    private TextBlock header;

    public String getHeaderText() {
        return header.getText();
    }
}

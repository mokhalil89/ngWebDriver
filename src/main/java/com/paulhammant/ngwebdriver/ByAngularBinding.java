package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.List;

public class ByAngularBinding extends ByAngular.BaseBy {

    public ByAngularBinding(String binding) {
        super();
        this.binding = binding;
    }

    private String binding;

    @Override
    public WebElement findElement(SearchContext context) {
        Object o = getObject(context);
        return ((List<WebElement>) o).get(0);
    }

    private Object getObject(SearchContext context) {
        JavascriptExecutor jse = getJavascriptExecutor(context);
        if (context instanceof WebDriver) {
            context = null;
        }
        Object o = jse.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var rootSelector = 'body';\n" +
                        "var exactMatch = false;\n" +
                        "var binding = '" + binding + "';\n" +
                        "\n" +
                        ByAngular.functions.get("findBindings")
                , context);
        errorIfNull(o);
        return o;
    }

    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        Object o = getObject(searchContext);
        errorIfNull(o);
        return (List<WebElement>) o;
    }

    @Override
    public String toString() {
        return "bindings(" + binding + ')';
    }
}
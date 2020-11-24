package com.devonfw.mts.cucumber.pages;

import com.devonfw.mts.cucumber.pages.widgets.*;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WidgetHelper {
    @Autowired
    private BrowserAccess browserAccess;

    public Widget widget(By by) {
        return new Widget(by, browserAccess.webDriver());
    }

    public Button button(By by) {
        return new Button(by, browserAccess.webDriver());
    }

    public Inputfield inputfield(By by) {
        return new Inputfield(by, browserAccess.webDriver());
    }

    public Checkbox checkbox(By by) {
        return new Checkbox(by, browserAccess.webDriver());
    }

    public Snackbar snackbar() {
        return new Snackbar(browserAccess.webDriver());
    }
}

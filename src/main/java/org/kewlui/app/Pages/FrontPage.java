package org.kewlui.app.Pages;

import com.gofintec.kewlui.base.StateData;
import com.gofintec.kewlui.controls.base.VisualControl;
import com.gofintec.kewlui.controls.base.settings.OverflowEnum;
import com.gofintec.kewlui.controls.std.Markdown;
import com.gofintec.kewlui.controls.std.VStack;

public class FrontPage {
    public void addPage (VisualControl parent, StateData globalState) {

        parent.setOverflowY(globalState, OverflowEnum.HIDDEN);
        var vstack = VStack.appendTo(parent,globalState);

        var markdownText = """
                ## KewlUI
                Congratulations your KewlUI application is running!
                """;
        var md = Markdown.appendTo(vstack, globalState, markdownText);



        // KewlUI - No guarantee or warranty. Use at your own risk.
    }
}

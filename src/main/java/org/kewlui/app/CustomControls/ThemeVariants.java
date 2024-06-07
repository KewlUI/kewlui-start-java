package org.kewlui.app.CustomControls;

import com.gofintec.kewlui.base.Form;
import com.gofintec.kewlui.base.StateData;
import com.gofintec.kewlui.controls.base.theme.VisualStyle;
import com.gofintec.kewlui.controls.std.BorderCard;
import com.gofintec.kewlui.controls.std.Button;

import java.util.HashMap;
import java.util.Map;

//////
// in this example, we
// 1] create a custom theme on the form
// 2] we make some new global variants/defaults for common controls which can be used later
public class ThemeVariants {

    public static void addThemesAndVariants (StateData globalState, Form form) {
        Map<String,VisualStyle> themes = new HashMap<>();
        addBespokeControlVariants(globalState, themes);
        form.setThemeConfig(globalState, themes);
    }


    // Variants allow storing config for controls under various names.  These can used by doing setVariant on a new button.
    // ie if we create a define a button 'customButton' with a number of default,
    // to use these defaults on other buttons we do otherButton.setVariant(state,"customButton");
    private static void addBespokeControlVariants (StateData globalState, Map<String, VisualStyle> themes) {
        VisualStyle myThemeStyle =  new VisualStyle();

        { // create a Button variant 'customButtonVariant' - you can source its config by using setVariant("customButtonVariant");
            Button b = new Button(globalState);
            b.setWidth(globalState, "300px").setBgColor(globalState, "theme.colors.secondary").setColor(globalState, "theme.colors.secondaryText");
            b.setIcon(globalState, "MdCall").setBorderRadius(globalState, "50%");
            myThemeStyle.setComponentVariant(b, globalState, "customButtonVariant");
        }

        themes.put("", myThemeStyle); // add in controls into the global space
    }
}

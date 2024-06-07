package org.kewlui.app.Forms;

/*
MIT License

Copyright (c) 2024 GoFintec Ltd

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/


import org.kewlui.app.Pages.FrontPage;
import org.kewlui.app.CustomControls.ThemeVariants;
import com.gofintec.kewlui.base.*;
import com.gofintec.kewlui.builders.SidebarBuilder;
import com.gofintec.kewlui.controls.base.VisualControl;
import com.gofintec.kewlui.controls.base.settings.ContainerPositionEnum;
import com.gofintec.kewlui.controls.base.settings.FontWeightEnum;
import com.gofintec.kewlui.controls.std.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;
import static com.gofintec.kewlui.base.ControlRequestReply.Ok;


/**
 * Constructs a dashboard form with various interactive components.
 * This class demonstrates the creation of a top-level dashboard page, integrating components
 * and pages through a tree structure for navigation.
 *
 * This page is registered by ui.getComponentFactory().setBuilder to map a component endpoint to a page
 */
public class DashboardForm {
    private static final Logger Log = LogManager.getLogger(DashboardForm.class);

    // Pages used within the dashboard
    private final FrontPage frontPage = new FrontPage();


    private static final String USER_NAME = "userName";

    /**
     * Creates the dashboard page, organizing its layout and content.
     *
     * @param type The type of dashboard to create.
     * @param optionalCreationInfo Additional information for dashboard creation.
     * @return The constructed Form object representing the dashboard.
     */
    public Form createDashboard (String type, String optionalCreationInfo) {
        Form form = Form.create();
        var top = form.getMainPanel();
        var globalState = form.getGlobalState();

        top.setFontFamily(globalState, "Calibri, Arial, sans-serif");
        // add from json file, all Material Design icons, e.g. 'MdDescription'
        form.setIconUrls(List.of("{urlBase}/resources/mdicons.json"));

        //"defaultTheme,solarFlare,green,darkFrost,darkMono,darkMoss,darkLilac,darkForest,darkMatrix,newElegance,vibrantContrast,girlChic,grayPrecision,customTheme,defaultTheme"
        form.setTheme(globalState, "newElegance"); // set the color scheme

        try  {
            var dashboard = DashboardLayout.appendTo(top, globalState);
            dashboard.setWestWidth(globalState, "200px");

            addApplicationPages(globalState, dashboard);

            var navbar = NavBarSimple.appendTo(dashboard, globalState, ContainerPositionEnum.HEADER);
            addNavbarSimple(globalState, navbar);

            ThemeVariants.addThemesAndVariants(globalState, form);
            return form;
        } catch (Exception e) {
            Log.error("");
            throw new RuntimeException(e);
        }
    }


    /**
     * Adds application pages to the dashboard.
     *
     * @param globalState The global state shared across pages.
     * @param dashboard The dashboard layout to add pages to.
     */
    private void addApplicationPages(StateData globalState, DashboardLayout dashboard) {
        //Create a tree of application pages on the sidebar
        // first param, shows tree representation
        // second param, MarkDown to show the user
        // third param, page creation
        var sideBar = SidebarBuilder.begin()
                .addPage("FrontPage","**Front Page**", (parent, state)-> frontPage.addPage(parent,state))
                /* // to make sub trees of pages
                .addPage("Examples","**Examples**", null, true)
                .addPage("Examples/Realtime","Realtime", (parent, state)-> realtimePage.addPage(parent,state))
                .addPage("GitHub","[GitHub](https://github.com/KewlUI)", null)
                */
                .appendTo(dashboard,globalState);
        sideBar.setWidth(globalState, "100%");
        Text.appendTo(sideBar, globalState, "Built with KewlUI", ContainerPositionEnum.SOUTH);
    }


    /**
     * Adds a simple navigation bar to the dashboard.
     *
     * @param state The state data to use.
     * @param navbar The visual control representing the navigation bar.
     */
    private static void addNavbarSimple (StateData state, VisualControl navbar) {

        var text = Heading.appendTo(navbar,state,"Cool App");
        text.setContainerPosition(state, ContainerPositionEnum.WEST);
        text.setFontWeight(state, FontWeightEnum.BOLD);

        var popover = PopoverSimple.appendTo(navbar,state, ContainerPositionEnum.EAST);
        {
            // create button for the popover
            var icon = Icon.appendTo(popover,state,ContainerPositionEnum.BUTTON);
            icon.setIcon(state, "MdAssignmentInd"); //https://react-icons.github.io/react-icons/icons/md/
            icon.setWidth(state, "40px").setHeight(state, "40px");
            icon.setMargin(state, "10px");

            // Example settings button
            var b = Button.appendTo(popover,state,"Settings");
            b.setIcon(state,"MdOutlineSettings").setWidth(state, "100%");

        }

        /*
        {
            var menuDrop = MenuDrop.appendTo(navbar,state, "Dashboard");
            MenuItem.appendTo(menuDrop,state,"Example Option").setOnClick(state, openDialogFunc);
            MenuItem.appendTo(menuDrop,state,"Load Page").setOnClick(state, openDialogFunc);
            MenuItem.appendTo(menuDrop,state,"Save Page").setOnClick(state, openDialogFunc);
        }
        */

    }



    /**
     * Handles user requests for the DashboardForm.
     *
     * @param requestEvent The event triggered by a user request.
     * @return A ControlRequestReply indicating the result of the request.
     */
    public static ControlRequestReply validateAndSetupUniqueStateForClient (ControlRequestEvent requestEvent)
    {
        ClientSession clientSession = requestEvent.getClientSession(); // browser tab
        ClientOverview overview = clientSession.getClientOwner();  // browser user, holds many possible tabs

        if (!overview.isLoggedIn()) {
            //return ControlRequestReply.Fail("User not logged in");
        }
        Log.info("Dashboard load  for session "+clientSession.getSessionId()+ " for user " + overview.getUserName());
        return Ok();
    }




}

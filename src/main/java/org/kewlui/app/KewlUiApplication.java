package org.kewlui.app;

import org.kewlui.app.Forms.DashboardForm;
import com.gofintec.kewlui.KewlUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Main application class for KewlUI.
 * This registers the main GET/POST endpoints under /kewlui path.
 * On requesting a new connection, callbacks will be fired to allow the local code to provide its own checks etc.
 */
public class KewlUiApplication {

	private static final Logger Log = LogManager.getLogger(KewlUiApplication.class);
	private static KewlUI kewlUI;
	private static DashboardForm dashboardWindow = new DashboardForm();

	public static void main(String[] args) {
		try {
			KewlUiApplication app = new KewlUiApplication();
			app.start(args); // Start KewlUI

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			reader.readLine();
		} catch (Exception e) {
			Log.error("Error starting application", e);
		}
	}



	public void start(String[] args) throws IOException {
		kewlUI = new KewlUI() {
			@Override
			public boolean connectionAuthorizationCallback (Map<String, String> headers) {
				return true; // this function returns true if authorisations etc are ok to connect
			}
		};
		kewlUI.setUriBase("/");

		//http.port=8080 https.port=8443 https.keystorePath=keystore.jks https.keystorePassword=changeit
		kewlUI.initializeServer(args);
		String license = new String(KewlUI.getResourceLoader().getResourceAsStream("static/license-key.txt").readAllBytes());
		KewlUI.setLicenseKey(license);

		addForms();
		kewlUI.start();
	}


	public KewlUI addForms () {
		kewlUI.getComponentFactory().setBuilder("*",
				(pageType, optionalVariantInfo) -> dashboardWindow.createDashboard(pageType, optionalVariantInfo),
				(request) -> dashboardWindow.validateAndSetupUniqueStateForClient(request)
		);


		kewlUI.setNewSessionValidation((clientOverview, clientSession) -> {
			// Add any other validation you might like here
			return true;
		});

		return kewlUI;
	}



}

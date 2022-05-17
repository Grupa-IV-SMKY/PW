package pl.edu.pbs;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.Route;

/**
 * A Designer generated component for the maine-ui template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("maine-ui")
@JsModule("./maine-ui.ts")
@Route("/maine-ui")
public class MaineUi extends LitTemplate {

    @Id("vaadinButton")
    private Button vaadinButton;

    /**
     * Creates a new MaineUi.
     */
    public MaineUi() {
        // You can initialise any data required for the connected UI components here.
        vaadinButton.addClickListener(event -> {
            Notification.show("Działa");
        });
    }

}

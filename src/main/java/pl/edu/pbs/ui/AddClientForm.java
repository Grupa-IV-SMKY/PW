package pl.edu.pbs.ui;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import pl.edu.pbs.model.Client;
import pl.edu.pbs.service.ClientService;

/**
 * A Designer generated component for the add-client-form template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("add-client-form")
@JsModule("./add-client-form.ts")
public class AddClientForm extends LitTemplate {

    @Id("clientName")
    private TextField clientName;
    @Id("clientMail")
    private TextField clientMail;
    @Id("clientPhone")
    private TextField clientPhone;
    @Id("saveClientBT")
    private Button saveClientBT;
    @Id("deleteClientBT")
    private Button deleteClientBT;
    @Id("closeFormBT")
    private Button closeFormBT;

    Binder<Client> binder = new BeanValidationBinder<>(Client.class);
    ClientService clientService;

    /**
     * Creates a new AddClientForm.
     */
    public AddClientForm(ClientService clientService) {
        // You can initialise any data required for the connected UI components here.
        this.clientService = clientService;

        saveClientBT.addClickListener(event -> validateAndSave());
        deleteClientBT.addClickListener(event -> fireEvent(new AddClientForm.DeleteEvent(this, binder.getBean())));
        closeFormBT.addClickListener(event -> fireEvent(new AddClientForm.CloseEvent(this)));

        binder.bindInstanceFields(this);

//        clientsCB.setItems(clientService.getAllClients());
//        clientsCB.setItemLabelGenerator(Client::getClientName);
//        clientsCB.addValueChangeListener(event -> {
//            if(event.getValue() != null){
//                setClient(event.getValue());
//            }
//        });
    }

    private void validateAndSave(){
        if(binder.isValid()){
            fireEvent(new AddClientForm.SaveEvent(this, binder.getBean()));
        }
    }

    public void setClient(Client client){
//        clientsCB.setItems(clientService.getAllClients());
        binder.setBean(client);
    }


    //Events handling
    public static abstract class AddClientFormEvent extends ComponentEvent<AddClientForm> {
        private final Client client;

        protected AddClientFormEvent(AddClientForm source, Client client){
            super(source, false);
            this.client=client;
        }

        public Client getClient(){
            return client;
        }
    }

    public static class SaveEvent extends AddClientForm.AddClientFormEvent {
        SaveEvent(AddClientForm source, Client client){
            super(source, client);
        }
    }

    public static class DeleteEvent extends AddClientForm.AddClientFormEvent {
        DeleteEvent(AddClientForm source, Client client){
            super(source, client);
        }
    }

    public static class CloseEvent extends AddClientForm.AddClientFormEvent {
        CloseEvent(AddClientForm source){
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType, listener);
    }

}

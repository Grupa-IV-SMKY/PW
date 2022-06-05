package pl.edu.pbs.ui;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import pl.edu.pbs.model.Client;
import pl.edu.pbs.model.Equipment;
import pl.edu.pbs.service.ClientService;

import java.util.Optional;

/**
 * A Designer generated component for the add-equipment-form template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("add-equipment-form")
@JsModule("./add-equipment-form.ts")
@Route("/add-equipment")
public class AddEquipmentForm extends LitTemplate {

    @Id("equipmentName")
    private TextField equipmentName;
    @Id("clientID")
    private ComboBox<Client> clientID;
    @Id("equipmentClientNotes")
    private TextField equipmentClientNotes;
    @Id("saveEquipmentBT")
    private Button saveEquipmentBT;
    @Id("deleteEquipmentBT")
    private Button deleteEquipmentBT;
    @Id("closeFormBT")
    private Button closeFormBT;
    @Id("issueEquipmentBT")
    private Button issueEquipmentBT;
    @Id("equipmentRepairNotes")
    private TextArea equipmentRepairNotes;
    Binder<Equipment> binder = new BeanValidationBinder<>(Equipment.class);
    ClientService clientService;

    /**
     * Creates a new AddEquipmentForm.
     */
    public AddEquipmentForm(ClientService clientService) {
        // You can initialise any data required for the connected UI components here.
        this.clientService = clientService;
        saveEquipmentBT.addClickListener(event -> validateAndSave());
        deleteEquipmentBT.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        closeFormBT.addClickListener(event -> fireEvent(new CloseEvent(this)));
        issueEquipmentBT.addClickListener(event -> fireEvent(new IssueEvent(this, binder.getBean())));

        clientID.setItems(clientService.getAllClients());
        clientID.setItemLabelGenerator(Client::getClientName);

        binder.forField(clientID)
                .withConverter(new ClientToIntConverter())
                .bind(Equipment::getClientID, Equipment::setClientID);

        binder.bindInstanceFields(this);
    }

    private void validateAndSave(){
        if(binder.isValid()){
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public void setEquipment(Equipment equipment){
        clientID.setItems(clientService.getAllClients());
        binder.setBean(equipment);
    }

    //Events handling
    public static abstract class AddEquipmentFormEvent extends ComponentEvent<AddEquipmentForm> {
        private final Equipment equipment;

        protected AddEquipmentFormEvent(AddEquipmentForm source, Equipment equipment){
            super(source, false);
            this.equipment=equipment;
        }

        public Equipment getEquipment(){
            return equipment;
        }
    }

    public static class SaveEvent extends AddEquipmentFormEvent{
        SaveEvent(AddEquipmentForm source, Equipment equipment){
            super(source, equipment);
        }
    }

    public static class DeleteEvent extends AddEquipmentFormEvent{
        DeleteEvent(AddEquipmentForm source, Equipment equipment){
            super(source, equipment);
        }
    }

    public static class CloseEvent extends AddEquipmentFormEvent{
        CloseEvent(AddEquipmentForm source){
            super(source, null);
        }
    }

    public static class IssueEvent extends AddEquipmentFormEvent{
        IssueEvent(AddEquipmentForm source, Equipment equipment){
            super(source, equipment);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType, listener);
    }

    public class ClientToIntConverter implements Converter<Client, Integer> {

        @Override
        public Result<Integer> convertToModel(Client client, ValueContext valueContext) {
            if(client == null){
                return Result.error("Driver can not be null");
            }
            return Result.ok(client.getClientID());
        }

        @Override
        public Client convertToPresentation(Integer integer, ValueContext valueContext) {
            if(integer == null){
                return new Client(-1, "Wybierz klienta", "Przykładowy mail", "Przykładowy nr tel");
            }
            Optional<Client> client = clientService.getClientById(integer);
            return client.orElse(null);
        }
    }
}

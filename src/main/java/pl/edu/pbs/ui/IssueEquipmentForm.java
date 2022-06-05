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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.converter.StringToFloatConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;
import org.apache.tomcat.jni.Time;
import pl.edu.pbs.model.Client;
import pl.edu.pbs.model.Equipment;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A Designer generated component for the issue-equipment-form template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("issue-equipment-form")
@JsModule("./issue-equipment-form.ts")
public class IssueEquipmentForm extends LitTemplate {

    @Id("equipmentRepairCost")
    private TextField equipmentRepairCost;
    @Id("equipmentRepairNotes")
    private TextField equipmentRepairNotes;
    @Id("saveIssueBT")
    private Button saveIssueBT;
    @Id("closeIssueBT")
    private Button closeIssueBT;

    private LocalDateTime equipmentIssueDate;
    Binder<Equipment> binder = new Binder<>(Equipment.class);

    public IssueEquipmentForm() {
        // You can initialise any data required for the connected UI components here.
        saveIssueBT.addClickListener(event -> validateAndSave());
        closeIssueBT.addClickListener(event -> fireEvent(new IssueEquipmentForm.CloseEvent(this)));

        binder.forField(equipmentRepairCost)
                .withConverter(new StringToFloatConverter("Nie powiodła się zmiana tekstu na liczbę"))
                .bind(Equipment::getEquipmentRepairCost, Equipment::setEquipmentRepairCost);

        binder.bindInstanceFields(this);
    }

    private void validateAndSave(){
        if(binder.isValid()){
            binder.getBean().setEquipmentIssueDate(LocalDateTime.now());
            binder.getBean().setEquipmentIsFixed(true);
            fireEvent(new IssueEquipmentForm.SaveEvent(this, binder.getBean()));
        }
    }

    public void setEquipment(Equipment equipment){
        binder.setBean(equipment);
    }

    //Events handling
    public static abstract class IssueEquipmentFormEvent extends ComponentEvent<IssueEquipmentForm> {
        private final Equipment equipment;

        protected IssueEquipmentFormEvent(IssueEquipmentForm source, Equipment equipment){
            super(source, false);
            this.equipment=equipment;
        }

        public Equipment getEquipment(){
            return equipment;
        }
    }

    public static class SaveEvent extends IssueEquipmentForm.IssueEquipmentFormEvent {
        SaveEvent(IssueEquipmentForm source, Equipment equipment){
            super(source, equipment);
        }
    }

    public static class CloseEvent extends IssueEquipmentForm.IssueEquipmentFormEvent {
        CloseEvent(IssueEquipmentForm source){
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType, listener);
    }

}

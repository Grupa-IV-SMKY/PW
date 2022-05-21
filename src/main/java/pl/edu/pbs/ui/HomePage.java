package pl.edu.pbs.ui;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import pl.edu.pbs.model.Client;
import pl.edu.pbs.model.Equipment;
import pl.edu.pbs.service.ClientService;
import pl.edu.pbs.service.EquipmentService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A Designer generated component for the home-page template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("home-page")
@JsModule("./home-page.ts")
@Route("/home")
public class HomePage extends LitTemplate {


    @Id("nameCB")
    private ComboBox<String> nameCB;
    @Id("clientCB")
    private ComboBox<Client> clientCB;
    @Id("fixedCB")
    private ComboBox<String> fixedCB;
    @Id("resetFiltersBT")
    private Button resetFiltersBT;
    @Id("addEquipmentBT")
    private Button addEquipmentBT;
    @Id("addClientBT")
    private Button addClientBT;
    @Id("equipmentGrid")
    private Grid<Equipment> equipmentGrid;
    @Id("clientGrid")
    private Grid<Client> clientGrid;
    @Id("issuedDP")
    private DatePicker issuedDP;
    @Id("admissionDP")
    private DatePicker admissionDP;
    @Id("addClientForm")
    private AddClientForm addClientForm;
    @Id("addEquipmentForm")
    private AddEquipmentForm addEquipmentForm;
    @Id("clientTF")
    private TextField clientTF;

    private final EquipmentService equipmentService;
    private List<Equipment> equipmentList;

    private final ClientService clientService;
    private List<Client> clientList;

    /**
     * Creates a new HomePage.
     */
    public HomePage(EquipmentService equipmentService, ClientService clientService) {
        // You can initialise any data required for the connected UI components here.
        this.equipmentService = equipmentService;
        this.clientService = clientService;

        equipmentList = equipmentService.getAllEquipment();
        clientList = clientService.getAllClients();

        equipmentGrid.addColumn(equipment -> equipment.getClientNameFromEquipment(clientService)).setHeader("Klient");
        equipmentGrid.addColumn(Equipment::getEquipmentName).setHeader("Nazwa sprzętu");
        equipmentGrid.addColumn(new LocalDateTimeRenderer<>(Equipment::getEquipmentAdmissionDate), "dd/MM/yyyy HH:mm").setHeader("Data przyjęcia");
        equipmentGrid.addColumn(Equipment::getEquipmentClientNotes).setHeader("Informacje od klienta");
        equipmentGrid.addColumn(Equipment::isEquipmentIsFixed).setHeader("Naprawiony");
        equipmentGrid.addColumn(Equipment::getEquipmentIssueDate).setHeader("Data wydania");
        equipmentGrid.addColumn(Equipment::getEquipmentRepairNotes).setHeader("Notatki z naprawy");
        equipmentGrid.addColumn(Equipment::getEquipmentRepairCost).setHeader("Koszt naprawy");
        equipmentGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        clientGrid.addColumn(Client::getClientName).setHeader("Nazwa klienta");
        clientGrid.addColumn(Client::getClientMail).setHeader("Mail klienta");
        clientGrid.addColumn(Client::getClientPhone).setHeader("Telefon klienta");
        clientGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        updateGrids();

        nameCB.setItems(equipmentList.stream().map(Equipment::getEquipmentName).distinct().collect(Collectors.toList()));
        clientCB.setItems(clientList);
        clientCB.setItemLabelGenerator(Client::getClientName);
        fixedCB.setItems("Tak", "Nie");

        addClientForm.setVisible(false);
        addEquipmentForm.setVisible(false);
    }

    @PostConstruct
    private void init(){
        resetFiltersBT.addClickListener(buttonClickEvent -> {
            nameCB.setValue(null);
            clientCB.setValue(null);
            fixedCB.setValue(null);
            issuedDP.setValue(null);
            admissionDP.setValue(null);
            closeClientForm();
            closeEquipmentForm();
        });

        nameCB.addValueChangeListener(value -> refreshEquipmentList());
        clientCB.addValueChangeListener(value -> {
            refreshEquipmentList();
            clientGrid.select(value.getValue());
        });
        fixedCB.addValueChangeListener(value -> refreshEquipmentList());
        issuedDP.addValueChangeListener(value -> refreshEquipmentList());
        admissionDP.addValueChangeListener(value -> refreshEquipmentList());

        clientGrid.asSingleSelect().addValueChangeListener(event -> handleClientSelect(event.getValue()));
        equipmentGrid.asSingleSelect().addValueChangeListener(event -> openEquipmentForm(event.getValue()));

        addClientForm.addListener(AddClientForm.SaveEvent.class, this::saveClient);
        addClientForm.addListener(AddClientForm.DeleteEvent.class, this::deleteClient);
        addClientForm.addListener(AddClientForm.CloseEvent.class, e -> closeClientForm());

        addEquipmentForm.addListener(AddEquipmentForm.SaveEvent.class, this::saveEquipment);
        addEquipmentForm.addListener(AddEquipmentForm.DeleteEvent.class, this::deleteEquipment);
        addEquipmentForm.addListener(AddEquipmentForm.CloseEvent.class, e -> closeEquipmentForm());

        addEquipmentBT.addClickListener(event -> openEquipmentForm(new Equipment()));
        addClientBT.addClickListener(event -> openClientForm(new Client()));

        clientTF.addValueChangeListener(event -> updateClientList());
        clientTF.setValueChangeMode(ValueChangeMode.LAZY);
    }

    private void updateClientList() {
        clientList = clientService.getClientByName(clientTF.getValue());
        updateGrids();
    }

    private void handleClientSelect(Client value) {
        openClientForm(value);
        clientCB.setValue(value);
    }

    private void openClientForm(Client client){
        if(client == null){
            closeClientForm();
        } else {
            addEquipmentForm.setVisible(false);
            addClientForm.setClient(client);
            addClientForm.setVisible(true);
        }
    }

    private void openEquipmentForm(Equipment equipment){
        if(equipment == null){
            closeEquipmentForm();
        } else {
            addClientForm.setVisible(false);
            addEquipmentForm.setEquipment(equipment);
            addEquipmentForm.setVisible(true);
        }
    }

    private void closeClientForm(){
        addClientForm.setVisible(false);
        updateGrids();
    }

    private void closeEquipmentForm(){
        addEquipmentForm.setVisible(false);
        updateEquipmentGrid();
    }

    private void saveClient(AddClientForm.SaveEvent event){
        clientService.saveClient(event.getClient());
        closeClientForm();
        clientList = clientService.getAllClients();
        updateGrids();
    }

    private void saveEquipment(AddEquipmentForm.SaveEvent event){
        equipmentService.saveEquipment(event.getEquipment());
        closeEquipmentForm();
        equipmentList = equipmentService.getAllEquipment();
        updateEquipmentGrid();
    }

    private void deleteClient(AddClientForm.DeleteEvent event){
        clientService.deleteClient(event.getClient());
        closeClientForm();
        clientList = clientService.getAllClients();
        updateGrids();
    }

    private void deleteEquipment(AddEquipmentForm.DeleteEvent event){
        equipmentService.deleteEquipment(event.getEquipment());
        closeEquipmentForm();
        equipmentList = equipmentService.getAllEquipment();
        updateEquipmentGrid();
    }

    private void refreshEquipmentList(){
        boolean isName = nameCB.getValue() != null;
        boolean isClient = clientCB.getValue() != null;
        boolean isFixedSelected = fixedCB.getValue() != null;
        boolean isIssued = issuedDP.getValue() != null;
        boolean isAdmissioned = admissionDP.getValue() != null;
        boolean isFixed = Objects.equals(fixedCB.getValue(), "Tak");

        equipmentList = equipmentService.getAllEquipment();

        if(isName){
            equipmentList = equipmentList.stream().filter(equipment -> Objects.equals(equipment.getEquipmentName(), nameCB.getValue())).collect(Collectors.toList());
        }
        if(isClient){
            equipmentList = equipmentList.stream().filter(equipment -> Objects.equals(equipment.getClientID(), clientCB.getValue().getClientID())).collect(Collectors.toList());
        }
        if(isFixedSelected){
            equipmentList = equipmentList.stream().filter(equipment -> Objects.equals(equipment.isEquipmentIsFixed(), isFixed)).collect(Collectors.toList());
        }
        if(isIssued){
            equipmentList = equipmentList.stream().filter(equipment -> Objects.equals(equipment.getEquipmentIssueDate().toLocalDate(), issuedDP.getValue())).collect(Collectors.toList());
        }
        if(isAdmissioned){
            equipmentList = equipmentList.stream().filter(equipment -> Objects.equals(equipment.getEquipmentAdmissionDate().toLocalDate(), admissionDP.getValue())).collect(Collectors.toList());
        }

        updateEquipmentGrid();
    }

    private void updateGrids(){
        equipmentGrid.setItems(equipmentList);
        clientGrid.setItems(clientList);
    }

    private void updateEquipmentGrid(){
        equipmentGrid.setItems(equipmentList);
    }

}

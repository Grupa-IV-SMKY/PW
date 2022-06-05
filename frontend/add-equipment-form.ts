import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/vaadin-form-layout/vaadin-form-layout.js';
import '@vaadin/combo-box/src/vaadin-combo-box.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '@vaadin/text-area/src/vaadin-text-area.js';

@customElement('add-equipment-form')
export class AddEquipmentForm extends LitElement {
  static get styles() {
    return css`
      :host {
          display: block;
          height: 100%;
      }
      `;
  }

  render() {
    return html`
<vaadin-form-layout style="width: 100%; height: 100%;">
 <vaadin-text-field id="equipmentName" type="text" placeholder="Podaj nazwę sprzętu:" label="Nazwa sprzętu"></vaadin-text-field>
 <vaadin-combo-box id="clientID" placeholder="Imię klienta" label="Klient"></vaadin-combo-box>
 <vaadin-text-field label="Notatki klienta" placeholder="Notatki od klienta" id="equipmentClientNotes" type="text"></vaadin-text-field>
 <vaadin-text-area label="Notatki technika" placeholder="Notatki z przebiegu naprawy" id="equipmentRepairNotes"></vaadin-text-area>
 <vaadin-button id="saveEquipmentBT" tabindex="0" theme="primary">
   Zapisz 
 </vaadin-button>
 <vaadin-button id="deleteEquipmentBT" tabindex="0" theme="error">
   Usuń 
 </vaadin-button>
 <vaadin-button id="closeFormBT" tabindex="0">
   Anuluj 
 </vaadin-button>
 <vaadin-button id="issueEquipmentBT" tabindex="0" theme="primary">
   Zdaj sprzęt 
 </vaadin-button>
</vaadin-form-layout>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}

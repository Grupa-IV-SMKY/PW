import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/horizontal-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/combo-box/src/vaadin-combo-box.js';
import '@vaadin/button/src/vaadin-button.js';
import '@vaadin/grid/src/vaadin-grid.js';
import '@vaadin/date-picker/src/vaadin-date-picker.js';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/form-layout/src/vaadin-form-layout.js';
import './add-client-form';
import './add-equipment-form';
import '@vaadin/text-field/src/vaadin-text-field.js';

@customElement('home-page')
export class MainUi extends LitElement {
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
<vaadin-vertical-layout style="width: 100%; height: 100%;">
 <vaadin-horizontal-layout theme="spacing" style="height: 100%; width: 100%;">
  <vaadin-vertical-layout theme="spacing" style="width: 30%; height: 100%;">
   <vaadin-text-field id="clientTF" label="Nazwa klienta" placeholder="Wyszukaj klienta po nazwie"></vaadin-text-field>
   <vaadin-grid style="flex:2; flex-shrink: 0; flex-grow: 1; height: 100%;" id="clientGrid" is-attached></vaadin-grid>
  </vaadin-vertical-layout>
  <vaadin-vertical-layout theme="spacing" style="width: 100%; height: 100%;">
   <vaadin-horizontal-layout theme="spacing" style="align-items: flex-end; width: 100%;">
    <vaadin-combo-box id="nameCB" label="Sprzęt"></vaadin-combo-box>
    <vaadin-date-picker label="Przyjęcie" placeholder="Wybierz datę przyjęcia" id="admissionDP"></vaadin-date-picker>
    <vaadin-combo-box id="clientCB" label="Klient"></vaadin-combo-box>
    <vaadin-date-picker label="Wydanie" placeholder="Wybierz datę wydania" id="issuedDP"></vaadin-date-picker>
    <vaadin-combo-box id="fixedCB" label="Naprawiony?"></vaadin-combo-box>
    <vaadin-button tabindex="0" id="resetFiltersBT">
      Resetuj filtry 
    </vaadin-button>
    <vaadin-button tabindex="0" id="addEquipmentBT">
      Dodaj sprzęt 
    </vaadin-button>
    <vaadin-button tabindex="0" id="addClientBT">
      Dodaj klienta 
    </vaadin-button>
   </vaadin-horizontal-layout>
   <vaadin-grid style="flex:2; flex-shrink: 0; flex-grow: 1; height: 100%;" id="equipmentGrid" is-attached></vaadin-grid>
  </vaadin-vertical-layout>
  <add-client-form id="addClientForm" style="flex:1; flex-shrink: 0; flex-grow: 0.5;"></add-client-form>
  <add-equipment-form id="addEquipmentForm" style="flex:1; flex-shrink: 0; flex-grow: 0.5;"></add-equipment-form>
 </vaadin-horizontal-layout>
</vaadin-vertical-layout>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}

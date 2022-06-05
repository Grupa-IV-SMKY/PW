import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/vaadin-form-layout/vaadin-form-layout.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '@vaadin/combo-box/src/vaadin-combo-box.js';

@customElement('issue-equipment-form')
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
 <vaadin-text-field id="equipmentRepairCost" type="text" placeholder="Podaj koszt naprawy:" label="Koszt naprawy"></vaadin-text-field>
 <vaadin-text-field label="Notatki technika" placeholder="Notatki z naprawy" id="equipmentRepairNotes" type="text"></vaadin-text-field>
 <vaadin-button id="saveIssueBT" tabindex="0" theme="primary">
   Zapisz 
 </vaadin-button>
 <vaadin-button id="closeIssueBT" tabindex="0">
   Anuluj 
 </vaadin-button>
</vaadin-form-layout>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}

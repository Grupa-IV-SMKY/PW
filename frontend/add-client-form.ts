import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/vaadin-form-layout/vaadin-form-layout.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '@vaadin/button/src/vaadin-button.js';

@customElement('add-client-form')
export class AddClientForm extends LitElement {
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
 <vaadin-text-field id="clientName" type="text" placeholder="Podaj imię i nazwisko" label="Imię i nazwisko"></vaadin-text-field>
 <vaadin-text-field id="clientMail" type="text" placeholder="Podaj mail" label="Adres mail"></vaadin-text-field>
 <vaadin-text-field id="clientPhone" type="text" placeholder="Podaj numer telefonu" label="Numer telefonu"></vaadin-text-field>
 <vaadin-button id="saveClientBT" tabindex="0" theme="primary">
   Zapisz 
 </vaadin-button>
 <vaadin-button id="deleteClientBT" tabindex="0" theme="error">
   Usuń 
 </vaadin-button>
 <vaadin-button id="closeFormBT" tabindex="0">
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

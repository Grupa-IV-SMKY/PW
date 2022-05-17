import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/button/src/vaadin-button.js';

@customElement('maine-ui')
export class MaineUi extends LitElement {
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
<vaadin-button id="vaadinButton">
  Button 
</vaadin-button>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}
